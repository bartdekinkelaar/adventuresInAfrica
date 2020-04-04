package AdventureInAfrica;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class Poep extends SpriteObject {
    private float snelheid = 7;
    private float richting = 180;

    public Poep(AfrikaAvontuur wereld) {
        super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("Poep.png")));
    }

	@Override
	public void update() {
		setDirectionSpeed(this.richting,this.snelheid);		
	}
}