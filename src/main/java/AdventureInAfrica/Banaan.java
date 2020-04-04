package AdventureInAfrica;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class Banaan extends SpriteObject {
    private float snelheid = 7;
    private float richting = 0;

    public Banaan(AfrikaAvontuur wereld) {
        super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("Banaan.png")));
    }

	@Override
	public void update() {
		setDirectionSpeed(this.richting,this.snelheid);		
	}
}