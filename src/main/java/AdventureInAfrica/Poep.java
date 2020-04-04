package AdventureInAfrica;
import java.util.List;
import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class Poep extends SpriteObject implements ICollidableWithGameObjects{
    private AfrikaAvontuur wereld;
    private float snelheid;
    private float richting;

    public Poep(AfrikaAvontuur wereld) {
        super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("poep.png")));
		this.wereld = wereld;
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go: collidedGameObjects) {
            if (go instanceof Speler) {
                wereld.deleteGameObject(this);
            }
            if (go instanceof Struik) {
                wereld.deleteGameObject(this);
            }
        }
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}