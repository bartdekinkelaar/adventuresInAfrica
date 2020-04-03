package AdventureInAfrica;
import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import nl.han.ica.oopg.objects.TextObject;
import nl.han.ica.oopg.view.View;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AfrikaAvontuur extends GameEngine {
	public NormaalAap[] normaalApen;
	public CoolAap[] coolApen;
	public KabouterAap[] kabouterApen;
	public WinterAap[] winterApen;
	public Struik[] struiken;
	public ArrayList<Banaan> bananen;
	public ArrayList<IPowerUp> powerUps;
	public ArrayList<Poep> poep;
	public Speler speler;
	public int highscoreSpeler;
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
	public TextObject highscoreText;
	public TextObject levensText;
	
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
		this.normaalApen = new NormaalAap[6];
		this.coolApen = new CoolAap[6];
		this.kabouterApen = new KabouterAap[6];
		this.winterApen = new WinterAap[6];
		this.levensSpeler = 3;
		this.highscoreSpeler = 0;
		
		int worldWidth = breedte;
		int worldHeight = hoogte;
		
		maakGameObjectenAan();
		View view = new View(worldWidth, worldHeight);
		setView(view);
		view.setBackground(240, 240, 240);
		size(worldWidth, worldHeight);
		tekenStartscherm(100, 100);
		tekenInfoveld();
	}

	@Override
	public void update() {
		// Dit doet nog niets }
	}

	public void tekenStartscherm(int dashboardWidth, int dashboardHeight) {
	}
	
	public void tekenInfoveld() {
		tekenHighscore();
		tekenLevens();
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
		int n = -1;
		for (int i = 0; i < 6; i++) {
			this.normaalApen[i] = new NormaalAap(this);
			this.coolApen[i] = new CoolAap(this);
			this.kabouterApen[i] = new KabouterAap(this);
			this.winterApen[i] = new WinterAap(this);
		}
			for (int k = 0; k < 6; k++) {
				n++;
				addGameObject(normaalApen[n], (breedte / 6) * k, 0);
				addGameObject(coolApen[n], (breedte / 6) * k, (hoogte / 4 /3));
				addGameObject(kabouterApen[n], (breedte / 6) * k, (hoogte / 4/3)*2);
				addGameObject(winterApen[n], (breedte / 6) * k, (hoogte / 4/3)*3);
			}
		}
	
	private void maakStruikenAan() {
		this.struiken = new Struik[3];
		this.struiken[0] = new Struik(this,0,(float) (hoogte-(hoogte/2.1)));
		this.struiken[1] = new Struik(this,2*breedte/5,(float) (hoogte-(hoogte/2.1)));
		this.struiken[2] = new Struik(this,4*breedte/5,(float) (hoogte-(hoogte/2.1)));
	}

	private void maakSpelerAan() {
		this.speler = new Speler(this);
		addGameObject(this.speler, breedte - (breedte / 2) - (this.getWidth()), hoogte - (hoogte / 4));
	}

	// gebruikt wanneer een aap doodgaat
	public void maakPowerUpAan() {
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
		Dashboard highscore  = new Dashboard(2, 2, 125, 100);
        highscoreText = new TextObject("Highscore:" + highscoreSpeler, 18);
        highscore.addGameObject(highscoreText);
        addDashboard(highscore);
	}
	
	public void tekenLevens() {
		Dashboard levens  = new Dashboard(100, 2, 200, 100);
        levensText = new TextObject("Levens:" + levensSpeler, 20);
        levens.addGameObject(levensText);
        addDashboard(levens);
	}
	
	public void leegGame() {
		
	}
}