package AdventureInAfrica;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.sound.Sound;

public class Speler extends SpriteObject {
	private AfrikaAvontuur wereld;

	public Speler(AfrikaAvontuur wereld) {
		// Met `.concat()` plak je 2 strings aan elkaar.
		// De methode returned een nieuwe String terug.
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("vreek.png")));
		this.wereld = wereld;
	}

	@Override
	public void update() {

	}

	public void keyPressed(int keyCode, char key) {
		final int speed = 5;
		if (keyCode == world.LEFT) {
			System.out.println("Ik druk op de links knop");
			setDirectionSpeed(270, speed);
		}
		if (keyCode == world.RIGHT) {
			System.out.println("Ik druk op de rechts knop");
			setDirectionSpeed(90, speed);
		}
	}
}