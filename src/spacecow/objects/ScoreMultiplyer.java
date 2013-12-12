package spacecow.objects;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

import spacecow.engine.Game;
import spacecow.engine.Score;
import spacecow.engine.TextureHandler;
import spacecow.engine.UnitCollission;

public class ScoreMultiplyer {

	private Texture smTex;
	private Rectangle smRect;
	private float x;
	float y;
	private float speed = 8;
	private static long nextObject = Sys.getTime()+100;
	private static ArrayList<ScoreMultiplyer> smArray = new ArrayList<>();
	private static ArrayList<ScoreMultiplyer> smRemove = new ArrayList<>();
	
	public ScoreMultiplyer() {
		smTex = TextureHandler.getInstance().getCowTex();
		this.y = -10-smTex.getTextureHeight();
		this.x = (float)Math.random()*Game.dWidth;
		smRect = new Rectangle(this.x, this.y, smTex.getWidth(), smTex.getHeight());
	}
	
	public void move(){
		this.y += this.speed;
		TextureHandler.drawTexture(this.smTex, this.x, this.y);
		this.smRect.setBounds(this.x, this.y, smTex.getTextureWidth(), smTex.getTextureHeight());
		if (colliding()) {
			Score.scoreMulti();
			smRemove.add(this);
		}
	}
	private boolean colliding(){
		boolean collission = UnitCollission.isColliding(smRect, Player.getInstance().getpRectangle());
		return collission;
	}
	public static void update(){
		for (ScoreMultiplyer dpR : smRemove) {
			smArray.remove(dpR);
		}
		if (Sys.getTime()>nextObject) {
			smArray.add(new ScoreMultiplyer());
			nextObject = (long) (Sys.getTime()+500+(Math.random()*5000)-(Score.getScoreMulti()*80));
		}
		for (ScoreMultiplyer dp : smArray) {
			dp.move();
			if (dp.y>Game.dHeight) {
				smRemove.add(dp);
				System.out.println(smArray.size());
			}
		}
	}
	public static long getNextObject() {
		return nextObject;
	}	
}
