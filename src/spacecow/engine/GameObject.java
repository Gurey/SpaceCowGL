package spacecow.engine;

import org.lwjgl.Sys;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

import spacecow.objects.ObjectArrays;
import spacecow.objects.Player;
import spacecow.objects.ScoreMultiplyer;

public class GameObject {

		private Texture smTex;
		private Rectangle smRect;
		private float x;
		float y;
		private float speed = 8;
		private static long nextObject = Sys.getTime()+100;
		
		
		public GameObject() {
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
//				ObjectArrays.getSmRemove().add(this);
			}
		}
		private boolean colliding(){
			boolean collission = UnitCollission.isColliding(smRect, Player.getInstance().getpRectangle());
			return collission;
		}
		public static void update(){
			for (ScoreMultiplyer dpR : ObjectArrays.getSmRemove()) {
				ObjectArrays.getSmArray().remove(dpR);
			}
			if (Sys.getTime()>nextObject) {
				ObjectArrays.getSmArray().add(new ScoreMultiplyer());
				nextObject = (long) (Sys.getTime()+500+(Math.random()*5000)-(Score.getScoreMulti()*80));
			}
			for (ScoreMultiplyer dp : ObjectArrays.getSmArray()) {
				dp.move();
				if (dp.y>Game.dHeight) {
					ObjectArrays.getSmRemove().add(dp);
				}
			}
		}
		public static long getNextObject() {
			return nextObject;
		}	
	
	
}
