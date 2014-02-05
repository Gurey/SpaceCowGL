package spacecow.objects;




import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameObject;
import spacecow.engine.Score;

public class ScoreMultiplyer extends GameObject {
	
	public ScoreMultiplyer(Texture tex, Score score, Player player) {
		super(tex, score, player);
		setMagnetic(false);
	}
	//if collision, the scoreMulti is increased by 1 and adds 1000 to score.
	public void collisionAction(){
		score.scoreMulti();
		score.incScore(1000);
		score.setMultiCol(score.getMultiCol()+1);
	}	
}
