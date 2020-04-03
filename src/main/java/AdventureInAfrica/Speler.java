package AdventureInAfrica;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class Speler extends SpriteObject {
	private AfrikaAvontuur wereld;
	public Speler(AfrikaAvontuur wereld) {
		// Met `.concat()` plak je 2 strings aan elkaar.
		// De methode returned een nieuwe String terug.
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("Vreek.png")));
		this.wereld = wereld;
	}

	@Override
	public void update() {
		if (getX() + getWidth() <= 0) {
			setX(wereld.width);
		}
	}
	
	@SuppressWarnings("static-access")
	public void keyPressed(int keyCode, char key) {
		final int speed = 5;
		final int still = 5;
		if (keyCode == wereld.LEFT) {
			System.out.println("Links: " + getX());
			setDirectionSpeed(270, speed);
		}
		if (keyCode == wereld.RIGHT) {
			System.out.println("Rechts: " + getX());
			setDirectionSpeed(90, speed);
		}
		if (keyCode == wereld.DOWN) {
			setDirectionSpeed(still, still);
		}
	}

	public void updateLevens(int levensAantal) {
		wereld.levensSpeler = levensAantal - 1;
	}
	
    public void mousePressed(int x, int y, int button) {
    	
	}
}