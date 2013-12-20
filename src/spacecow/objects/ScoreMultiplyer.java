package spacecow.objects;


import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameObject;
import spacecow.engine.Score;

public class ScoreMultiplyer extends GameObject {
	
	public ScoreMultiplyer(Texture tex) {
		super(tex);
	}
	public void collisionAction(){
		Score.scoreMulti();
	}	
}
