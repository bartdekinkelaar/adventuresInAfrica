package AdventureInAfrica;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

import java.util.List;
import java.util.Timer;

public class Speler extends SpriteObject implements ICollidableWithGameObjects {
	private AfrikaAvontuur wereld;
	public Timer schietTimer;
	long start;
	long end;
	long nieuwSchot;
	long laatsteSchot;
	long verschil;
	long schotInterval;

	public Speler(AfrikaAvontuur wereld) {
		// Met `.concat()` plak je 2 strings aan elkaar.
		// De methode returned een nieuwe String terug.
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("Vreek.png")));
		this.wereld = wereld;
		schietTimer = new Timer();
	}

	@Override
	public void update() {
		if (getX() + getWidth() <= 0) {
			setX(wereld.width);
		}
		if (getX() + getWidth() > (wereld.width + getWidth())) {
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
		if (keyCode == wereld.UP) {
			controleerSchot();
		}
	}

	public void controleerSchot() {
		if (start == 0 && end == 0) {
			start = System.nanoTime();
		}
		if (start != 0 && end == 0) {
			end = start;
			start = System.nanoTime();
			checkIntervalSchieten(end, start);
			if (verschil > 1) {
				System.out.println("Laatste schot:" + laatsteSchot);
				System.out.println("Nieuwste schot:" + nieuwSchot);
				System.out.println(verschil + "seconden");
				laatsteSchot = end;
				System.out.println("geschoten");
				end = start;
				this.wereld.addGameObject(new Banaan(wereld), this.x + this.getWidth() / 2, this.y + this.getHeight());
			}
		}
		if (start != 0 && end != 0) {
			end = start;
			start = System.nanoTime();
			checkIntervalSchieten(end, start);
			if (laatsteSchot != 0) {
				nieuwSchot = start;
				checkIntervalSchieten(laatsteSchot, nieuwSchot);
				if (verschil > 1) {
					System.out.println("Laatste schot:" + laatsteSchot);
					System.out.println("Nieuwste schot:" + nieuwSchot);
					System.out.println(verschil + "seconden");
					laatsteSchot = nieuwSchot;
					System.out.println("geschoten");
					this.wereld.addGameObject(new Banaan(wereld), this.x, this.y);

				}
			} else if (laatsteSchot == 0) {
				laatsteSchot = start;
			}
		}
	}

	public void checkIntervalSchieten(long tijdEen, long tijdTwee) {
		verschil = (tijdTwee - tijdEen) / 1000000000;
	}

	public void updateLevens(int levensAantal) {
		wereld.levensSpeler = levensAantal - 1;
		wereld.updateHighscore();
	}

	public void mouseClicked(int x, int y, int button) {
		if (x > getX() && y > getY()) {
			if (x < (getX() + getWidth()) && y < (getY() + getHeight())) {
				System.out.println("Speler aangeklikt");
//				wereld.leegGame();
			}
		}
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			if (go instanceof Poep) {
				wereld.setLevensSpeler(wereld.getLevensSpeler()-1);
				wereld.deleteGameObject(go);
			}
		}
	}
}