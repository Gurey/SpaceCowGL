package spacecow.objects;




import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameObject;
import spacecow.engine.Score;

public class ScoreMultiplyer extends GameObject {
	
	public ScoreMultiplyer(Texture tex, Score score, Player player) {
		super(tex, score, player);
	}
	public void collisionAction(){
		score.scoreMulti();
	}	
}
