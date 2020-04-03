package AdventureInAfrica;

import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import processing.core.PGraphics;

public class Struik extends GameObject{
	AfrikaAvontuur wereld;
	private StruikSprite struik;
	private Sprite fstruik1;
	private Sprite fstruik2;
	private Sprite fstruik3;
	public int aantalKeerGeraakt;
	private float x;
	private float y;
	
	@SuppressWarnings("static-access")
	Struik(AfrikaAvontuur wereld, float x, float y){
		this.wereld = wereld;
		this.fstruik1 = new Sprite(wereld.MEDIA_URL.concat("StruikEen.png"));
		this.fstruik2 = new Sprite(wereld.MEDIA_URL.concat("StruikTwee.png"));
		this.fstruik3 = new Sprite(wereld.MEDIA_URL.concat("StruikDrie.png"));
		this.x = x;
		this.y = y;
		this.aantalKeerGeraakt = 0;
		this.struik = new StruikSprite(fstruik1);
		wereld.addGameObject(this.struik,this.x,this.y);
	}

	@Override
	public void update() {
		if(aantalKeerGeraakt == 0) {
			this.struik = new StruikSprite(fstruik2);
			wereld.addGameObject(this.struik,this.x,this.y);
		}
		if(aantalKeerGeraakt == 1) {
			wereld.deleteGameObject(this.struik);
			this.struik = new StruikSprite(fstruik2);
			wereld.addGameObject(this.struik,this.x,this.y);
		}
		if(aantalKeerGeraakt == 2) {
			wereld.deleteGameObject(this.struik);
			this.struik = new StruikSprite(fstruik3);
			wereld.addGameObject(this.struik,this.x,this.y);
		}
		if(aantalKeerGeraakt == 3) {
			wereld.deleteGameObject(this.struik);
		}
		if(aantalKeerGeraakt > 3) {
		}
	}

	@Override
	public void draw(PGraphics g) {
		// TODO Auto-generated method stub
		
	}
	public void refresh() {
        aantalKeerGeraakt = 0;
    }
	public void wordtGeraakt() {
		aantalKeerGeraakt++;
	}
}
