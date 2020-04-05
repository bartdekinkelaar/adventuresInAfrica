package AdventureInAfrica;
import java.util.List;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;

public class KabouterAap extends Aap implements IApen, ICollidableWithGameObjects {


	KabouterAap(AfrikaAvontuur wereld) {
		super(wereld, 40,new Sprite(AfrikaAvontuur.MEDIA_URL.concat("KabouterAap.png")));
	}
	
	/** 
	* This method is a resume of the most methods this class has.
	*/
	@Override
	public void geraaktActie() {
		telPuntenOp();
		double random = Math.random();
		removeFromArray();
		if (random < 0.33) {
			wereld.maakPowerUpAan(this.x, this.y);
		}
	}
	
	/** 
	* This method removes the monkey from the array of monkey's in the game
	*/
	private void removeFromArray() {                               
		for (int i = 0; i < wereld.getKabouterApen().length; i++) { 
			if (wereld.getKabouterApen()[i] == this) {              
				wereld.getKabouterApen()[i] = null;                 
			}                                                      
		}                                                          
	}                                                              
	
	/** 
	* This method added the points of the shot monkey to the current points
	*/
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
