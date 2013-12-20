package spacecow.objects;


import org.lwjgl.Sys;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

import spacecow.engine.Game;
import spacecow.engine.GameObject;
import spacecow.engine.Score;
import spacecow.engine.TextureHandler;
import spacecow.engine.UnitCollission;

public class StarBuff extends GameObject {
	
	private static int addStar=0;
	public StarBuff(Texture tex) {
		super(tex);
		this.setRotating(true);
		this.setRotationSpeed((float) (1+Math.random()*4));
	}
	@Override
	public void collisionAction(){
		addStar++;
		for (int i = 0; i <= Score.getScoreMulti(); i++) {
			Score.incScore(10);
		}
		if (addStar>=10) {
			Star.addNewStar();
			addStar=0;
		}
	}
//	private boolean colliding(){
//		boolean collision = UnitCollission.isColliding(sbRect, Player.getInstance().getpRectangle());
//		if (collision) {
//			
//		}
//		return collision;
//	}
//	public static void update(){
//		for (StarBuff sbR : GameObjectHandler.getInstance().getSbRemove()) {
//			GameObjectHandler.getInstance().getSbArray().remove(sbR);
//		}
//		if (Sys.getTime()>nextObject) {
//			GameObjectHandler.getInstance().getSbArray().add(new StarBuff());
//			nextObject = (long) (Sys.getTime()+(Math.random()*50));
//		}
//		for (StarBuff sb : GameObjectHandler.getInstance().getSbArray()) {
//			sb.move();
//			if (sb.y>Game.dHeight) {
//				GameObjectHandler.getInstance().getSbRemove().add(sb);
//			}
//		}
//	}
}
