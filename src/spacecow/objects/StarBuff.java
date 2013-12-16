package spacecow.objects;


import org.lwjgl.Sys;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

import spacecow.engine.Game;
import spacecow.engine.Score;
import spacecow.engine.TextureHandler;
import spacecow.engine.UnitCollission;

public class StarBuff {

	private Texture sbTex;
	private Rectangle sbRect;
	private float x;
	private float y;
	private float speed = 20;
	private static long nextObject = Sys.getTime()+100;
	private static int addStar;
	
	public StarBuff() {
		sbTex = TextureHandler.getInstance().getStarBuffTex();
		this.y = -10-sbTex.getTextureHeight();
		this.x = (float)Math.random()*Game.dWidth-sbTex.getTextureWidth();
		sbRect = new Rectangle(this.x, this.y, sbTex.getWidth(), sbTex.getHeight());
	}
	
	public void move(){
		this.y += this.speed;
		TextureHandler.drawTexture(this.sbTex, this.x, this.y);
		this.sbRect.setBounds(this.x, this.y, sbTex.getTextureWidth(), sbTex.getTextureHeight());
		if (colliding()) {
			ObjectArrays.getSbRemove().add(this);
		}
	}
	private boolean colliding(){
		boolean collission = UnitCollission.isColliding(sbRect, Player.getInstance().getpRectangle());
		if (collission) {
			addStar++;
			for (int i = 0; i <= Score.getScoreMulti(); i++) {
				Score.incScore(10);
			}
			if (addStar>=10) {
				Star.addNewStar();
				addStar=0;
			}
		}
		return collission;
	}
	public static void update(){
		for (StarBuff sbR : ObjectArrays.getSbRemove()) {
			ObjectArrays.getSbArray().remove(sbR);
		}
		if (Sys.getTime()>nextObject) {
			ObjectArrays.getSbArray().add(new StarBuff());
			nextObject = (long) (Sys.getTime()+(Math.random()*50));
		}
		for (StarBuff sb : ObjectArrays.getSbArray()) {
			sb.move();
			if (sb.y>Game.dHeight) {
				ObjectArrays.getSbRemove().add(sb);
			}
		}
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
