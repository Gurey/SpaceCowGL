package spacecow.objects;

import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameObject;
import spacecow.engine.Score;
import spacecow.engine.Time;

public class Asteroid extends GameObject {

	private Time time;
	
	public Asteroid(Texture tex, Score score, Player player, Time time) {
		super(tex, score, player);
		this.setMagnetic(false); 
		this.setRotating(true);
		this.time = time;
		this.setRotationSpeed((float)(-4+Math.random()*8));
		this.setSpeed((float) (getSpeed()+(Math.random()*3)));
	}
	
	//Reduce the time left with x seconds if colliding
	@Override
	public void collisionAction(){
		time.incTimeLeft(-10);
		score.setAstroidCol(score.getAstroidCol()+1);
		score.reduceScorePercent(5);
	}

}
