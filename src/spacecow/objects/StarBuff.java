package spacecow.objects;

import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameObject;
import spacecow.engine.Score;

public class StarBuff extends GameObject {
	
	private static int addStar=0;
	public StarBuff(Texture tex, Score score, Player player) {
		super(tex, score, player);
		this.setRotating(true);
		this.setRotationSpeed((float) (-5+Math.random()*10));
	}
	//if collision addStar is increased by 1 and the score is increased.
	//addStar is used in GameObjectHandler, when reached to 10 it adds a new star to the background.
	@Override
	public void collisionAction(){
		addStar++;
		score.incScore(100*score.getScoreMulti());
		score.setStarCol(score.getStarCol()+1);
	}
	public static int getAddStar() {
		return addStar;
	}
	public static void setAddStar(int addStar) {
		StarBuff.addStar = addStar;
	}
}
