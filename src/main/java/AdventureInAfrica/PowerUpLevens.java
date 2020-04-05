package AdventureInAfrica;

import java.util.List;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import processing.core.PGraphics;

public class PowerUpLevens extends SpriteObject implements ICollidableWithGameObjects, IPowerUp {
	private AfrikaAvontuur wereld;

	public PowerUpLevens(AfrikaAvontuur wereld) {
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("gemRed.png")));
		this.wereld = wereld;
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
				System.out.println("trigger");
			}
		}
	}

	@Override
	public void powerUpActie() {
		int levens = wereld.getLevensSpeler();
		if (levens < 3) {
			this.wereld.setLevensSpeler(levens + 1);
		}
		this.wereld.deleteGameObject(this);
	}
}