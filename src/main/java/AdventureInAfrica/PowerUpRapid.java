package AdventureInAfrica;
import java.util.List;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import processing.core.PGraphics;

public class PowerUpRapid extends SpriteObject implements ICollidableWithGameObjects, IPowerUp {
	private AfrikaAvontuur wereld;

	public PowerUpRapid(AfrikaAvontuur wereld) {
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("gemYellow.png")));
		this.wereld = wereld;
		this.setDirectionSpeed(180, 40);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(PGraphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			if (go instanceof Speler) {
				this.powerUpActie();
			}
		}
	}

	@Override
	public void powerUpActie() {
		wereld.powerUpStart = System.nanoTime();
		this.wereld.activeerPowerUpRapid();
		this.wereld.deleteGameObject(this);
	}
}
