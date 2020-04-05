package AdventureInAfrica;

import java.util.List;
import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import processing.core.PGraphics;

public class Struik extends GameObject implements ICollidableWithGameObjects{
	AfrikaAvontuur wereld;
	private StruikSprite struik;
	private Sprite fstruik1;
	private Sprite fstruik2;
	private Sprite fstruik3;
	public int aantalKeerGeraakt;
	private float x;
	private float y;

	@SuppressWarnings("static-access")
	Struik(AfrikaAvontuur wereld, float x, float y) {
		this.wereld = wereld;
		this.fstruik1 = new Sprite(wereld.MEDIA_URL.concat("StruikEen.png"));
		this.fstruik2 = new Sprite(wereld.MEDIA_URL.concat("StruikTwee.png"));
		this.fstruik3 = new Sprite(wereld.MEDIA_URL.concat("StruikDrie.png"));
		this.x = x;
		this.y = y;
		this.aantalKeerGeraakt = 0;
		this.struik = new StruikSprite(fstruik1,this);
		wereld.addGameObject(this.struik, this.x, this.y);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(PGraphics g) {

	}

	public void refresh() {
		aantalKeerGeraakt = 0;
		wereld.deleteGameObject(struik);
		this.struik = new StruikSprite(fstruik1,this);
		wereld.addGameObject(this.struik, this.x, this.y);
	}
	
	/** 
	* This is the method for if the bush gets attacked by poop from the apes
	*/
	public void wordtGeraakt() {
		aantalKeerGeraakt++;
		if (aantalKeerGeraakt == 0) {
			this.struik = new StruikSprite(fstruik1,this);
			wereld.addGameObject(this.struik, this.x, this.y);
		}
		if (aantalKeerGeraakt == 1) {
			wereld.deleteGameObject(this.struik);
			this.struik = new StruikSprite(fstruik2,this);
			wereld.addGameObject(this.struik, this.x, this.y);
		}
		if (aantalKeerGeraakt == 2) {
			wereld.deleteGameObject(this.struik);
			this.struik = new StruikSprite(fstruik3,this);
			wereld.addGameObject(this.struik, this.x, this.y);
		}
		if (aantalKeerGeraakt == 3) {
			wereld.deleteGameObject(this.struik);
		}
		if (aantalKeerGeraakt > 3) {
		}
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			if (go instanceof Poep) {
				this.wordtGeraakt();
				wereld.deleteGameObject(go);
				System.out.println("tt");
			}
		}
	}
}