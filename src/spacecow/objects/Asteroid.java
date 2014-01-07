package spacecow.objects;

import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameObject;
import spacecow.engine.Score;
import spacecow.engine.Time;

public class Asteroid extends GameObject {

	Time time = new Time();
	
	public Asteroid(Texture tex, Score score, Player player) {
		super(tex, score, player);
		this.setMagnetic(false); 
		this.setRotating(true);
		this.setRotationSpeed((float)(-4+Math.random()*8));
	}
	
	//Reduce the time left with x seconds if colliding
	@Override
	public void collisionAction(){
		time.incTimeLeft(-10);
	}

}
