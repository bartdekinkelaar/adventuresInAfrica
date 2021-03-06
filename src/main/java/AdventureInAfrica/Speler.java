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

	public Speler(AfrikaAvontuur wereld) {
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

	/** 
	* This method checks if the interval between two shootings is above the minimum-interval 
	*/
	public void controleerSchot() {
		if (start == 0 && end == 0) {
			start = System.nanoTime();
			this.wereld.addGameObject(new Banaan(wereld), this.x, this.y);
		}
		if (start != 0 && end == 0) {
			end = start;
			start = System.nanoTime();
			checkIntervalSchieten(end, start);
			if (verschil > wereld.schotInterval) {
				laatsteSchot = end;
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
				if (verschil > wereld.schotInterval) {
					laatsteSchot = nieuwSchot;
					this.wereld.addGameObject(new Banaan(wereld), this.x, this.y);

				}
			} else if (laatsteSchot == 0) {
				laatsteSchot = start;
			}
		}
	}
	/** 
	* This sets the time from two moments (the interval) from nanoseconds to round seconds.
	*/
	public void checkIntervalSchieten(long tijdEen, long tijdTwee) {
		verschil = (tijdTwee - tijdEen) / 1000000000;
	}
	

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			if (go instanceof Poep) {
				wereld.setLevensSpeler(wereld.getLevensSpeler() - 1);
				wereld.deleteGameObject(go);
			}
			if (go instanceof PowerUpLevens) {
				this.wereld.setLevensSpeler(wereld.getLevensSpeler() + 1);				
				wereld.deleteGameObject(go);
			}
			if (go instanceof PowerUpRapid) {
				this.wereld.startRapid(System.nanoTime());
				wereld.deleteGameObject(go);
			}
			if (go instanceof PowerUpStruik) {
				this.wereld.getStruiken()[0].refresh();
				this.wereld.getStruiken()[1].refresh();
				this.wereld.getStruiken()[2].refresh();
				wereld.deleteGameObject(go);
			}
		}
	}
}