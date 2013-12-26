package spacecow.objects;

import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameObject;
import spacecow.engine.Score;

public class StarBuff extends GameObject {
	
	private static int addStar=0;
	public StarBuff(Texture tex, Score score, Player player) {
		super(tex, score, player);
		this.setRotating(true);
		this.setRotationSpeed((float) (1+Math.random()*4));
	}
	@Override
	public void collisionAction(){
		addStar++;
		score.incScore(10*score.getScoreMulti());
	}
	public static int getAddStar() {
		return addStar;
	}
	public static void setAddStar(int addStar) {
		StarBuff.addStar = addStar;
	}
}
