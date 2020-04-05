package AdventureInAfrica;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public abstract class Aap extends SpriteObject{
	protected float punten;
	protected AfrikaAvontuur wereld;
	protected boolean moveRight = true;
	protected float movement = 0;
	protected float speed = (float) 0.9;
	protected Sprite sprite;
	
	Aap(AfrikaAvontuur wereld, int punten, Sprite sprite){
		super(sprite);
		this.wereld = wereld;
		this.punten = punten;
	}
}
