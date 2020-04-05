package AdventureInAfrica;

import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("serial")
public class AfrikaAvontuur extends GameEngine {
	public NormaalAap[] normaalApen;
	public CoolAap[] coolApen;
	public KabouterAap[] kabouterApen;
	public WinterAap[] winterApen;
	public Struik[] struiken;
	public Speler speler;
	public int scoreSpeler;
	public int levensSpeler;
	public int breedte = 1000;
	public int hoogte = 800;
	public String statusSpel;
	public float score = 0;
	public long tijdPowerUpRapidActief;
	public long laatsteTijdSchietenBanaan;
	public long cooldownBanaan;
	public long cooldownPoep;
	public GameObject hartje;
	public Sprite logoImage;
	public SpriteObject logo;
	public TextObject scoreText;
	public TextObject levensText;
	public TextObject eindText;
	public int schietSnelheid;
	public int barHoogte;
	public int apenPerRij;
	public int aantalApen;
	public long verschil;
	public long start;
	public long eind;
	public long laatstePoep;
	public long nieuwPoep;
	public double poepInterval;
	public long powerUpStart;
	public long powerUpTime;

	public static String MEDIA_URL = "src/main/java/AdventureInAfrica/media/";

	public static void main(String[] args) {
		AfrikaAvontuur hw = new AfrikaAvontuur();

		hw.runSketch();
	}

	// test
	@Override
	public void setupGame() {
		this.apenPerRij = 5;
		this.aantalApen = apenPerRij * 4;
		this.normaalApen = new NormaalAap[apenPerRij];
		this.coolApen = new CoolAap[apenPerRij];
		this.kabouterApen = new KabouterAap[apenPerRij];
		this.winterApen = new WinterAap[apenPerRij];
		this.levensSpeler = 3;
		this.scoreSpeler = 0;
		this.schietSnelheid = 500;
		this.barHoogte = 25;
		this.poepInterval = 0.5;
		speler.schotInterval = 1.0;

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
		this.genereerPoep();

		if (aantalApen < 16) {
			poepInterval = 0.3;
		}
		if (aantalApen < 8) {
			poepInterval = 0.2;
		}
		if (aantalApen == 0 || levensSpeler == 0) {
			leegGame();
		}
	}

	public void tekenInfoveld() {
		tekenHighscore();
		tekenLevens();
	}

	// is hier zodat de setupgame niet te vol wordt
	private void maakGameObjectenAan() {
		maakApenAan();
		maakStruikenAan();
		maakStruikenAan();
		maakSpelerAan();
	}

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

	private void maakStruikenAan() {
		this.struiken = new Struik[3];
		this.struiken[0] = new Struik(this, 0, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[1] = new Struik(this, 2 * breedte / 5, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[2] = new Struik(this, 4 * breedte / 5, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[0] = new Struik(this, 0, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[1] = new Struik(this, 2 * breedte / 5, (float) (hoogte - (hoogte / 2.1)));
		this.struiken[2] = new Struik(this, 4 * breedte / 5, (float) (hoogte - (hoogte / 2.1)));
	}

	private void maakSpelerAan() {
		this.speler = new Speler(this);
		addGameObject(this.speler, breedte - (breedte / 2) - (this.getWidth()), hoogte - (hoogte / 4) - barHoogte);
	}

	// gebruikt wanneer een aap doodgaat
	public void maakPowerUpAan(float x, float y) {
		this.addGameObject(new PowerUpLevens(this), this.getSpeler().getX(),
				this.getSpeler().getY() - this.getSpeler().getHeight());
	}

	public void activeerPowerUpRapid() {
		long activatiePowerUp = System.nanoTime();
		powerUpTime = checkInterval(activatiePowerUp, powerUpStart);
		while(powerUpTime < 10) {
			speler.schotInterval = 0.5;
			System.out.println("Rapid powerup actief");
		}
	}

	public Speler getSpeler() {
		return speler;
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

	public void tekenHighscore() {
		Dashboard highscore = new Dashboard(0, 0, 120, barHoogte);
		scoreText = new TextObject("Score: " + this.score, 18);
		highscore.addGameObject(scoreText);
		addDashboard(highscore);
	}

	public void tekenLevens() {
		Dashboard levens = new Dashboard(75, 0, 200, barHoogte);
		levensText = new TextObject("Levens:" + levensSpeler, 18);
		levens.addGameObject(levensText);
		addDashboard(levens);
	}

	public void tekenEindscherm() {
		Dashboard eindscherm = new Dashboard(175, 150, 600, 200);
		eindText = new TextObject("Eindscore:" + this.score, 48);
		eindscherm.addGameObject(eindText);
		addDashboard(eindscherm);
	}

	public void updateHighscore() {
		scoreText.setText("Score: " + this.score);
	}

	public void updateLevens() {
		levensText.setText("Levens: " + levensSpeler);
	}

	public void leegGame() {
		deleteAllGameOBjects();
		tekenEindscherm();
	}

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
					System.out.println("Laatste schot:" + laatstePoep);
					System.out.println("Nieuwste schot:" + nieuwPoep);
					System.out.println(verschil + "seconden");
					laatstePoep = nieuwPoep;
					System.out.println("geschoten");
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

	public long checkInterval(long tijdEen, long tijdTwee) {
		return (tijdTwee - tijdEen) / 1000000000;
	}
	
//	public void plaatsPowerUp() {
//		if(aantalApen % 5 == 0 && aantalApen > 0) {
//			Random randPower = new Random();
//			int randomNum = randPower.nextInt(3-1);
//			if(randomNum == 1) {
//				
//			}
//			
//		}
//	}
}