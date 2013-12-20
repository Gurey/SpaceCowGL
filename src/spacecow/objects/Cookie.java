package spacecow.objects;

import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;

import spacecow.engine.Game;
import spacecow.engine.GameObject;
import spacecow.engine.Score;
import spacecow.engine.TextureHandler;
import spacecow.engine.Time;

public class Cookie extends GameObject {

	private static long nextObject = Sys.getTime()+100, activationTime;
	private static boolean active = false;
	private static int lastMulti;
	Time time = new Time();
	
	public Cookie(Texture tex){
		super(tex);
		this.setRotating(true);
		this.setRotationSpeed(5);
	}	
	@Override
	public void collisionAction(){
		time.incTimeLeft(10);
		Score.incScore(1000);
	}
	
}
