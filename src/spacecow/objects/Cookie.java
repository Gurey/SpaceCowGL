package spacecow.objects;


import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameObject;
import spacecow.engine.Score;
import spacecow.engine.Time;

public class Cookie extends GameObject {

	Time time = new Time();
	
	public Cookie(Texture tex, Score score, Player player){
		super(tex, score, player);
		this.setRotating(true);
		this.setRotationSpeed(5);
	}	
	@Override
	public void collisionAction(){
		time.incTimeLeft(10);
		getScore().incScore(1000);
	}
	
}
