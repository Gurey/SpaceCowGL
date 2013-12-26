package spacecow.objects;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;

import spacecow.buffs.SuperSpeed;
import spacecow.engine.Game;
import spacecow.engine.Score;
import spacecow.engine.TextureHandler;


public class Star{

	float gbWidth = Game.dWidth, gbHeight = Game.dHeight;
	
	Random ran = new Random();
	
	private double starRanSpeed = 0;
	
	private Score score;
	
	private double yVel=0;
	
	
	private float centerY, centerX;
	
	Texture starIm = null;
	private TextureHandler texHandler;

	
	
	private SuperSpeed superSpeed;
	private double minSpeed;
	private double maxSpeed;
	
	public Star(Score score, TextureHandler texHandler, SuperSpeed superSpeed){
		this.centerX=(int) (0+Math.random()*gbWidth);
		this.centerY=(int) (0+Math.random()*gbHeight);
		this.score = score;
		this.texHandler = texHandler;
		this.superSpeed = superSpeed;
		this.minSpeed=0.3*superSpeed.getSuperSpeed();
		this.maxSpeed=15*superSpeed.getSuperSpeed();
		this.yVel=getStarRanSpeed();
		setStarim(this);
	}
	public Star(Score score, TextureHandler texHandler ,int y, SuperSpeed superSpeed){
		this.centerX=(int) (0+Math.random()*gbWidth);
		this.centerY=y;
		this.score = score;
		this.texHandler = texHandler;
		this.superSpeed = superSpeed;
		this.minSpeed=0.3*superSpeed.getSuperSpeed();
		this.maxSpeed=15*superSpeed.getSuperSpeed();
		this.yVel=getStarRanSpeed();
		setStarim(this);
	}
	public void changePos(double yVel){
		this.centerY+=yVel*superSpeed.getSuperSpeed();
	}
	
	public void move(){
 			this.changePos(this.yVel);
			texHandler.drawTexture(this.starIm, this.getCenterX(), this.centerY);
			if (this.centerY>gbHeight) {
				score.incScoreBackground(1);
				this.yVel=getStarRanSpeed();
				this.centerX=(int) (0+Math.random()*gbWidth);
				setStarim(this);
				this.centerY=0-starIm.getTextureHeight();
			}
	}

	public double getStarRanSpeed() {
		starRanSpeed = (double) minSpeed+(Math.random()*maxSpeed);
		return  starRanSpeed;
	}
	public double getyVel() {
		return yVel;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	
	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}
	private void setStarim(Star s){
		if (this.yVel > maxSpeed*0.95) {
			this.starIm = texHandler.getStar1File();
		}
		else if (this.yVel > maxSpeed*0.7 && this.yVel < maxSpeed*0.95) {
			this.starIm = texHandler.getStar2File();
		}
		else if (this.yVel > maxSpeed*0.5 && this.yVel < maxSpeed*0.7) {
			this.starIm = texHandler.getStar3File();
		}
		else if (this.yVel > maxSpeed*0.2 && this.yVel < maxSpeed*0.5) {
			this.starIm = texHandler.getStar4File();
		}
		else{
			this.starIm = texHandler.getStar5File();
		}
		
	}
}
