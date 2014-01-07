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
	//is use when the game is started
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
	//Used when the game is running.
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
	//Moves the Star
	public void changePos(double yVel){
		this.centerY+=yVel*superSpeed.getSuperSpeed();
	}

	public void move(){
		this.changePos(this.yVel); 												//moves the star
		texHandler.drawTexture(this.starIm, this.getCenterX(), this.centerY);	//Draws the star
		if (this.centerY>gbHeight) { 											//if the star is outside the display
			score.incScore(1); 													//score is increased by 1
			this.yVel=getStarRanSpeed(); 										//give the star a new random speed
			this.centerX=(int) (0+Math.random()*gbWidth); 						//give the star and new random X position 
			setStarim(this);													//Give the Star a new image based on the new speed
			this.centerY=0-starIm.getTextureHeight();							//Set the Y-value to the top of the screen.
		}
	}
	//return a random speed.
	public double getStarRanSpeed() {
		starRanSpeed = (double) minSpeed+(Math.random()*maxSpeed);
		return  starRanSpeed;
	}
	//Sets a texture to the star based on the speed of the star. 
	//if its slow it gets a smaller picture
	//if its fast it gets a big picture.
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
	
}
