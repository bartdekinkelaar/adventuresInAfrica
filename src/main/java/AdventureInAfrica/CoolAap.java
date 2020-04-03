package AdventureInAfrica;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class CoolAap extends SpriteObject implements IApen {
	private float punten = 20;
	private AfrikaAvontuur wereld;
	private boolean moveRight = true;
	private float movement = 0;
	private float speed = (float) 0.7;

	CoolAap(AfrikaAvontuur wereld) {
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("Aap.png")));
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
		if (moveRight) {
			this.x = this.x + this.speed;
			this.movement = this.movement + this.speed;
		}
		if (!moveRight) {
			this.x = this.x - this.speed;
			this.movement = this.movement - this.speed;
		}
		if ((this.movement >= 50 && moveRight) || (this.movement <= 0 && !moveRight)) {
			moveRight = !moveRight;

		}
	}
}
