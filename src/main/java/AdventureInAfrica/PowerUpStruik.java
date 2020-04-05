package AdventureInAfrica;
import java.util.List;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import processing.core.PGraphics;

public class PowerUpStruik extends SpriteObject implements ICollidableWithGameObjects, IPowerUp{
	private AfrikaAvontuur wereld;
	
	public PowerUpStruik(AfrikaAvontuur wereld) {
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("gemGreen.png")));
		this.wereld = wereld;
		this.setDirectionSpeed(180,40);   
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void draw(PGraphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go: collidedGameObjects) {
			if (go instanceof Speler) {				
				this.powerUpActie();
			}
		}	
	}

	@Override
	public void powerUpActie() {
		this.wereld.getStruiken()[0].refresh();
		this.wereld.getStruiken()[1].refresh();
		this.wereld.getStruiken()[2].refresh();
		this.wereld.deleteGameObject(this);
	}
}