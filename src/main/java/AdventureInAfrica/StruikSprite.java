package AdventureInAfrica;

import java.util.List;

import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class StruikSprite extends SpriteObject implements ICollidableWithGameObjects{

	private Struik master;
	
	StruikSprite(Sprite sprite, Struik master) {
		super(sprite);
		this.master = master;
	}

	@Override
	public void update() {
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			if (go instanceof Poep) {
				master.wordtGeraakt();
				master.wereld.deleteGameObject(go);
			}
		}
	}
}
