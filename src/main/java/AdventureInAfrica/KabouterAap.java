package AdventureInAfrica;

import java.util.List;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class KabouterAap extends SpriteObject implements IApen, ICollidableWithGameObjects {
	private float punten = 10;
	private AfrikaAvontuur wereld;
	private boolean moveRight = true;
	private float movement = 0;
	private float speed = (float) 0.9;

	KabouterAap(AfrikaAvontuur wereld) {
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("KabouterAap.png")));
		this.wereld = wereld; 
	}

	@Override
	public void geraaktActie() {
		telPuntenOp();
		double random = Math.random();
		if (random < 0.33) {
			wereld.maakPowerUpAan(this.x, this.y);
		}
	}

	public void telPuntenOp() {
		this.wereld.setScore(this.wereld.getScore() + this.punten);
	}

	@Override
	public void update() {
		if(moveRight) {
			this.x = this.x + this.speed;
			this.movement = this.movement + this.speed;
		}
		if(!moveRight) {
			this.x = this.x - this.speed;
			this.movement = this.movement - this.speed;
		}
		if((this.movement >= (this.wereld.getWidth()/5)-(1.3*this.getWidth()) && moveRight)||(this.movement <= 0 && !moveRight)) {
			moveRight = !moveRight;
		}
	}
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			if (go instanceof Banaan) {
				wereld.deleteGameObject(go);
				this.geraaktActie();
				wereld.deleteGameObject(this);
			}
		}
	}
}
