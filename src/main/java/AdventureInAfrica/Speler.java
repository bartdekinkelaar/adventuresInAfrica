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
		if(getX() + getWidth() > (wereld.width + getWidth())) {
			setX(0 - getWidth());
		}
	}

	@SuppressWarnings("static-access")
	public void keyPressed(int keyCode, char key) {
		final int speed = 5;
		final int still = 0;
		if (keyCode == wereld.LEFT) {
			setDirectionSpeed(270, speed);
		}
		if (keyCode == wereld.RIGHT) {
			setDirectionSpeed(90, speed);
		}
		if (keyCode == wereld.DOWN) {
			setDirectionSpeed(still, still);
		}
	}
}