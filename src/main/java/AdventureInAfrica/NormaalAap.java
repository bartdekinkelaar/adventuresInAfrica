package AdventureInAfrica;

import java.util.List;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;

public class NormaalAap extends Aap implements IApen, ICollidableWithGameObjects {



	NormaalAap(AfrikaAvontuur wereld) {
		super(wereld, 10,new Sprite(AfrikaAvontuur.MEDIA_URL.concat("Aap.png")));
	}

	@Override
	public void geraaktActie() {
		telPuntenOp();
		removeFromArray();
		wereld.maakPowerUpAan(this.x, this.y);

	}

	private void removeFromArray() {
		for (int i = 0; i < wereld.getNormaalApen().length; i++) {
			if (wereld.getNormaalApen()[i] == this) {
				wereld.getNormaalApen()[i] = null;
			}
		}
	}

	public void telPuntenOp() {
		this.wereld.setScore(this.wereld.getScore() + this.punten);
	}

	@Override
	public void update() {
		if (moveRight) {
			this.x = this.x + this.speed;
			this.movement = this.movement + this.speed;
		}
		if (!moveRight) {
			this.x = this.x - this.speed;
			this.movement = this.movement - this.speed;
		}
		if ((this.movement >= (this.wereld.getWidth() / 5) - (1.3 * this.getWidth()) && moveRight)
				|| (this.movement <= 0 && !moveRight)) {
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
