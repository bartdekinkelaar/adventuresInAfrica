package AdventureInAfrica;

import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.view.View;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AfrikaAvontuur extends GameEngine {
	public ArrayList<IApen> apen;
	public ArrayList<Struik> struiken;
	public ArrayList<Banaan> bananen;
	public ArrayList<IPowerUp> powerUps;
	public ArrayList<Poep> poep;
	public Speler speler;
	public int levensSpeler;
	public String statusSpel;
	public float score;
	public long tijdPowerUpRapidActief;
	public long laatsteTijdSchietenBanaan;
	public long cooldownBanaan;
	public long cooldownPoep;
	// Deze regel maakt het makkelijker om te refereren naar je plaatjes.
	public static String MEDIA_URL = "src/main/java/AdventureInAfrica/media/";

	public static void main(String[] args) {
		AfrikaAvontuur hw = new AfrikaAvontuur();

		// deze methode start de game.
		hw.runSketch();
	}

	// test
	@Override
	public void setupGame() {
		int worldWidth = 500;
		int worldHeight = 500;
		TextObject to = new TextObject("Hello, World!", 40);
		to.setForeColor(255, 255, 255, 255);
		addGameObject(to, 100, 100);
		maakGameObjectenAan();
		View view = new View(worldWidth, worldHeight);
		setView(view);
		size(worldWidth, worldHeight);
	}

	@Override
	public void update() {
		// Dit doet nog niets }
	}

	public void tekenStartscherm() {

	}

	public void tekenEindScherm() {

	}

	// is hier zodat de setupgame niet te vol wordt
	private void maakGameObjectenAan() {
		maakApenAan();
		maakStruikenAan();
		maakStruikenAan();
		maakSpelerAan();
	}

	private void maakApenAan() {

	}

	private void maakStruikenAan() {

	}

	private void maakSpelerAan() {
		this.speler = new Speler(this);
		addGameObject(this.speler);
	}

	// gebruikt wanneer een aap doodgaat
	public void maakPowerUpAan(){
	}

	public void activeerPowerUpRapid() {
		// m.b.v. Timer
	}

	// wordt aangeroepen door speler wss
	public void schietBanaanAf() {

	}

	public ArrayList<IApen> getApen() {
		return apen;
	}

	public Speler getSpeler() {
		return speler;
	}

	public ArrayList<Struik> getStruiken() {
		return struiken;
	}

	public void setLevensSpeler(int levens) {

	}
	public float getScore() {
		return this.score;
	}
	public void setScore(float score) {
		this.score = score;
	}
}
