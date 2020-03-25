package AdventureInAfrica;

import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;

public class WinterAap extends SpriteObject implements IApen{
	private float punten = 40;
	private AfrikaAvontuur wereld;
	
	WinterAap(AfrikaAvontuur wereld){
		super(new Sprite(AfrikaAvontuur.MEDIA_URL.concat("Aap.png")));
		this.wereld = wereld;
	}
	
	@Override
	public void geraaktActie() {
		telPuntenOp();	
		double random = Math.random();
		if(random < 0.33) {
			wereld.maakPowerUpAan();
		}
	}

	public void telPuntenOp() {
		this.wereld.setScore(this.wereld.getScore()+this.punten);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
