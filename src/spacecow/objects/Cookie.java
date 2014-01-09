package spacecow.objects;


import org.newdawn.slick.opengl.Texture;

import spacecow.buffs.Magnet;
import spacecow.engine.GameObject;
import spacecow.engine.Score;
import spacecow.engine.Time;

public class Cookie extends GameObject {
	
	Time time;
	
	public Cookie(Texture tex, Score score, Player player, Magnet magnet, Time time){
		super(tex, score, player);
		this.time = time;
		this.setMagnetic(false);
		this.setRotating(true);
		this.setRotationSpeed((float)(-7+Math.random()*14));
	}	
	//Increase the time with x seconds and Score with x
	@Override
	public void collisionAction(){
		time.incTimeLeft(10);
		score.incScore(10000);
	}
	
}
