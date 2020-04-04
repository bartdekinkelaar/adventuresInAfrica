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

	// Deze regel maakt het makkelijker om te refereren naar je plaatjes.
	public static String MEDIA_URL = "src/main/java/AdventureInAfrica/media/";

	public static void main(String[] args) {
		AfrikaAvontuur hw = new AfrikaAvontuur();

		// deze methode start de game.
		hw.runSketch();
	}

	@Override
	public void setupGame() {
		this.apenPerRij = 6;
		this.normaalApen = new NormaalAap[apenPerRij];
		this.coolApen = new CoolAap[apenPerRij];
		this.kabouterApen = new KabouterAap[apenPerRij];
		this.winterApen = new WinterAap[apenPerRij];
		this.levensSpeler = 3;
		this.scoreSpeler = 0;
		this.schietSnelheid = 500;
		this.barHoogte = 25;
		this.aantalApen = apenPerRij * 4;
		
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
		if (aantalApen == 0) {
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
			addGameObject(kabouterApen[n], (breedte / apenPerRij) * k, (hoogte / 3 / 3));
			addGameObject(coolApen[n], (breedte / apenPerRij) * k, (hoogte / 3 / 3) * 2);
			addGameObject(normaalApen[n], (breedte / apenPerRij) * k, (hoogte / 3 / 3) * 3);
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

//		if (random >= (double)0.33 && random < 0.66) {
//			addGameObject(new PowerUpStruik(this),x,y);
//		}
//		if (random >= (double)0.66) {
//			addGameObject(new PowerUpRapid(this),x,y);
//		}
	}

	public void activeerPowerUpRapid() {
		// m.b.v. Timer
	}

	// wordt aangeroepen door speler wss
	public void schietBanaanAf() {

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
		Dashboard score = new Dashboard(0, 0, 120, barHoogte);
		scoreText = new TextObject("Score: " + this.score, 18);
		score.addGameObject(scoreText);
		addDashboard(score);
	}

	public void updateHighscore() {
		scoreText.setText("Score: " + this.score);
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

	public void leegGame() {
		deleteAllGameOBjects();
		tekenEindscherm();
	}
}