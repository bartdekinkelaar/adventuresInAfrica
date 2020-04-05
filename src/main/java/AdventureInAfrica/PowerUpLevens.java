package AdventureInAfrica;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class PowerUpLevens extends SpriteObject {

    private float snelheid = 7;
    private float richting = 180;

    public PowerUpLevens(AfrikaAvontuur wereld) {
        super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("gemRed.png")));
    }

	@Override
	public void update() {
		setDirectionSpeed(this.richting,this.snelheid);		
	}
}