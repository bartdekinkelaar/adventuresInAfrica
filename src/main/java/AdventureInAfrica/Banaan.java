package AdventureInAfrica;
import java.util.List;
import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class Banaan extends SpriteObject implements ICollidableWithGameObjects{
    private AfrikaAvontuur wereld;
    private float snelheid;
    private float richting;

    public Banaan(AfrikaAvontuur wereld) {
        super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("banaan.png")));
        this.wereld = wereld;
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for (GameObject go: collidedGameObjects) {
            if (go instanceof Apen) {
                wereld.deleteGameObject(this);
            }
        }
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}