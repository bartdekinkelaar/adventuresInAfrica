package AdventureInAfrica;
import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.engine.GameEngine;
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
	public int levensSpeler;
	public int breedte = 1000;
	public int hoogte = 700;
	public String statusSpel;
	public float score = 0;
	public long tijdPowerUpRapidActief;
	public long laatsteTijdSchietenBanaan;
	public long cooldownBanaan;
	public long cooldownPoep;
	public Sprite logoImage;
	public SpriteObject logo;
	public TextObject dashboardText;
	
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
		int worldWidth = breedte;
		int worldHeight = hoogte;
		
		createDashboard(100, 100);
		maakGameObjectenAan();
		View view = new View(worldWidth, worldHeight);
		setView(view);
		size(worldWidth, worldHeight);
		this.logoImage = new Sprite(MEDIA_URL.concat("Beginscherm_logo.png"));
	}

	@Override
	public void update() {
		// Dit doet nog niets
	}

	public void tekenStartscherm() {
		TextObject testTekst = new TextObject("Welkom in de game guys", 33);
		addGameObject(testTekst);
		addGameObject(this.logo, 0, 0);
	}

	public void tekenEindScherm() {

	}
	
	private void createDashboard(int dashboardWidth, int dashboardHeight) {
        Dashboard dashboard = new Dashboard(100,100, 100, 100);
        dashboardText = new TextObject("welkom", 1);
        dashboard.addGameObject(dashboardText);
        addDashboard(dashboard);
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
		this.struiken[0] = new Struik(this,0,hoogte-(hoogte/3));
		this.struiken[1] = new Struik(this,2*breedte/5,hoogte-(hoogte/3));
		this.struiken[2] = new Struik(this,4*breedte/5,hoogte-(hoogte/3));
	}

	private void maakSpelerAan() {
		this.speler = new Speler(this);
		addGameObject(this.speler);
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
	
//	private void refreshDasboardText() {
//        dashboardText.setText("Bubbles popped: ");
//    }
}
