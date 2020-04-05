package AdventureInAfrica;
import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.view.View;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class AfrikaAvontuur extends GameEngine {
	public NormaalAap[] normaalApen;
	public CoolAap[] coolApen;
	public KabouterAap[] kabouterApen;
	public WinterAap[] winterApen;
	public Struik[] struiken;
	public Speler speler;
	public int levensSpeler;
	public int breedte = 1000;
	public int hoogte = 800;
	public float score = 0;
	public TextObject scoreText;
	public TextObject levensText;
	public TextObject eindText;
	public int barHoogte;
	public int schietSnelheid;
	public int apenPerRij;
	public long verschil;
	public long start;
	public long eind;
	public long laatstePoep;
	public long nieuwPoep;
	public double poepInterval;
	public double schotInterval;
	public long powerUpActivatie;
	public long currentTime;
	public boolean rapidIsActive;

	public static String MEDIA_URL = "src/main/java/AdventureInAfrica/media/";

	public static void main(String[] args) {
		AfrikaAvontuur hw = new AfrikaAvontuur();
		hw.runSketch();
	}

	@Override
	public void setupGame() {
		this.apenPerRij = 5;
		this.normaalApen = new NormaalAap[apenPerRij];
		this.coolApen = new CoolAap[apenPerRij];
		this.kabouterApen = new KabouterAap[apenPerRij];
		this.winterApen = new WinterAap[apenPerRij];
		this.levensSpeler = 3;
		this.schietSnelheid = 500;
		this.barHoogte = 25;
		this.poepInterval = 0.5;
		this.schotInterval = 1.0;

		int worldWidth = breedte;
		int worldHeight = hoogte;

		maakGameObjectenAan();
		View view = new View(worldWidth, worldHeight);
		setView(view);
		view.setBackground(240, 240, 240);
		size(worldWidth, worldHeight);
		tekenInfoveld();
	}

	@Override
	public void update() {
		this.updateHighscore();
		this.updateLevens();

		if (this.apenLevend().size() > 0) {
			this.genereerPoep();
		}

		if (this.apenLevend().size() <= 8) {
			poepInterval = 0.15;
		}

		if (this.apenLevend().size() < 16 && this.apenLevend().size() > 8) {
			poepInterval = 0.3;
		}

		if (this.apenLevend().size() == 0 || levensSpeler == 0) {
			leegGame();
		}
		if (rapidIsActive == true) {
			checkRapidTime();
		}
	}

	
	/** 
	* This makes the 'textbar' on top of the screen, with the highscore and the number of lives.
	*/
	public void tekenInfoveld() {
		tekenHighscore();
		tekenLevens();
	}
	
	/** 
	* This method is added to keep the setupGame-part clean
	*/
	private void maakGameObjectenAan() {
		maakApenAan();
		maakStruikenAan();
		maakSpelerAan();
	}
	
	/** 
	* This method makes new instances of the object "aap" (apes)
	*/
	private void maakApenAan() {
		int n = -1;
		for (int i = 0; i < apenPerRij; i++) {
			this.normaalApen[i] = new NormaalAap(this);
			this.coolApen[i] = new CoolAap(this);
			this.kabouterApen[i] = new KabouterAap(this);
			this.winterApen[i] = new WinterAap(this);
		}
		for (int k = 0; k < apenPerRij; k++) {
			n++;
			addGameObject(winterApen[n], (breedte / apenPerRij) * k, 0);
			addGameObject(kabouterApen[n], (breedte / apenPerRij) * k, (hoogte / 9));
			addGameObject(coolApen[n], (breedte / apenPerRij) * k, (hoogte / 9) * 2);
			addGameObject(normaalApen[n], (breedte / apenPerRij) * k, (hoogte / 9) * 3);
		}
	}

	/** 
	* This method makes new instances of the object "Struik" (bush)
	*/
	private void maakStruikenAan() {
		this.struiken = new Struik[3];
		this.struiken[0] = new Struik(this, 0, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[1] = new Struik(this, 2 * breedte / 5, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[2] = new Struik(this, 4 * breedte / 5, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[0] = new Struik(this, 0, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[1] = new Struik(this, 2 * breedte / 5, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[2] = new Struik(this, 4 * breedte / 5, (float) (hoogte - (hoogte / 2.1)));
	}

	/** 
	* This method makes the instance of the player and adds it into the game.
	*/
	private void maakSpelerAan() {
		this.speler = new Speler(this);
		addGameObject(this.speler, breedte - (breedte / 2) - (this.getWidth()), hoogte - (hoogte / 4) - barHoogte);
	}

	/** 
	* This makes a new instance of a powerup and adds it into the current game
	*/
	// gebruikt wanneer een aap doodgaat
	public void maakPowerUpAan(float x, float y) {
		Random r = new Random();
		if (r.nextInt(3) != 1) {
			int rI = r.nextInt(3);
			if (rI == 0) {
				addGameObject(new PowerUpLevens(this), x, y);
			}
			if (rI == 1) {
				addGameObject(new PowerUpStruik(this), x, y);
			}
			if (rI == 2) {
				addGameObject(new PowerUpRapid(this), x, y);
			}
		}
	}

	public Struik[] getStruiken() {
		return struiken;
	}

	public int getLevensSpeler() {
		return levensSpeler;
	}

	public void setLevensSpeler(int levens) {
		this.levensSpeler = levens;
	}

	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public NormaalAap[] getNormaalApen() {
		return normaalApen;
	}

	public CoolAap[] getCoolApen() {
		return coolApen;
	}

	public KabouterAap[] getKabouterApen() {
		return kabouterApen;
	}

	public WinterAap[] getWinterApen() {
		return winterApen;
	}

	public void setNormaalApen(NormaalAap[] normaalApen) {
		this.normaalApen = normaalApen;
	}

	public void setCoolApen(CoolAap[] coolApen) {
		this.coolApen = coolApen;
	}

	public void setKabouterApen(KabouterAap[] kabouterApen) {
		this.kabouterApen = kabouterApen;
	}

	public void setWinterApen(WinterAap[] winterApen) {
		this.winterApen = winterApen;
	}

	/** 
	* This method makes the text with the score on top of screen when the game starts 
	*/
	public void tekenHighscore() {
		Dashboard highscore = new Dashboard(0, 0, 120, barHoogte);
		scoreText = new TextObject("Score: " + this.score, 18);
		highscore.addGameObject(scoreText);
		addDashboard(highscore);
	}
	
	/** 
	* This method makes the text with the number of lives on top of screen when the game starts 
	*/
	public void tekenLevens() {
		Dashboard levens = new Dashboard(75, 0, 200, barHoogte);
		levensText = new TextObject("Levens:" + levensSpeler, 18);
		levens.addGameObject(levensText);
		addDashboard(levens);
	}

	/** 
	* This method shows the score after the game has been ended
	*/
	public void tekenEindscherm() {
		Dashboard eindscherm = new Dashboard(175, 150, 600, 200);
		eindText = new TextObject("Eindscore:" + this.score, 48);
		eindscherm.addGameObject(eindText);
		addDashboard(eindscherm);
	}

	/** 
	* This method updates the highscore text on every moment, because it is used in update()
	*/
	public void updateHighscore() {
		scoreText.setText("Score: " + this.score);
	}

	/** 
	* When a player lost one life or received one, this method updated the text in the screen
	*/
	public void updateLevens() {
		levensText.setText("Levens: " + levensSpeler);
	}

	/** 
	* This method makes the whole game empty from objects except textObjects.
	*/
	public void leegGame() {
		deleteAllGameOBjects();
		tekenEindscherm();
	}
	
	/** 
	* This method generates the poop inside the game
	* It ensures that the poop falls from random apes & at different moments
	*/
	private void genereerPoep() {
		Random rand = new Random();
		Poep p = new Poep(this);
		GameObject o = this.apenLevend().get(rand.nextInt(this.apenLevend().size()));
		if (start == 0 && eind == 0) {
			start = System.nanoTime();
		}
		if (start != 0 && eind == 0) {
			eind = start;
			start = System.nanoTime();
			verschil = checkInterval(eind, start);
			if (verschil > poepInterval) {

				addGameObject(p, o.getX(), o.getY());
				start = System.nanoTime();
			}
		}
		if (start != 0 && eind != 0) {
			eind = start;
			start = System.nanoTime();
			checkInterval(eind, start);
			if (laatstePoep != 0) {
				nieuwPoep = start;
				verschil = checkInterval(laatstePoep, nieuwPoep);
				if (verschil > 1) {
					laatstePoep = nieuwPoep;
					addGameObject(new Poep(this), o.getX(), o.getY());

				}
			} else if (laatstePoep == 0) {
				laatstePoep = start;
			}
		}
	}

	private ArrayList<GameObject> apenLevend() {
		ArrayList<GameObject> go = new ArrayList<GameObject>();
		for (int i = 0; i < this.normaalApen.length; i++) {
			if (this.normaalApen[i] != null) {
				go.add((GameObject) normaalApen[i]);
			}
		}
		for (int i = 0; i < this.coolApen.length; i++) {
			if (this.coolApen[i] != null) {
				go.add((GameObject) coolApen[i]);
			}
		}
		for (int i = 0; i < this.kabouterApen.length; i++) {
			if (this.kabouterApen[i] != null) {
				go.add((GameObject) kabouterApen[i]);
			}
		}
		for (int i = 0; i < this.winterApen.length; i++) {
			if (this.winterApen[i] != null) {
				go.add((GameObject) winterApen[i]);
			}
		}
		return go;
	}

	/** 
	* This method returns the time between two moments, from nanoseconds, to seconds
	*/
	public long checkInterval(long tijdEen, long tijdTwee) {
		return (tijdTwee - tijdEen) / 1000000000;
	}
	
	/** 
	* This method starts the powerup Rapid.
	*/
	public void startRapid(long powerupStart) {
		powerUpActivatie = powerupStart;
		schotInterval = 0.5;
		rapidIsActive = true;
	}
	
	/** 
	* This method checks if the time for the Rapid-powerup has ended or not.
	* And if the time has been ended, it resets the boolean which remember its powerup-state
	*/
	public void checkRapidTime() {
		if (((System.nanoTime() - powerUpActivatie) / 1000000000) > 9) {
			schotInterval = 1;
			rapidIsActive = false;
		}
	}
}