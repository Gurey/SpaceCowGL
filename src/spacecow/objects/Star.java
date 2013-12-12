package spacecow.objects;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;

import spacecow.engine.Game;
import spacecow.engine.Score;
import spacecow.engine.TextureHandler;


public class Star{

	float gbWidth = Game.dWidth, gbHeight = Game.dHeight;
	
	Random ran = new Random();
	
	private static double starRanSpeed = 0;
	
	private static int starCount = 0;

	
	private double yVel=0;
	
	
	private float centerY, centerX;
	
	Texture starIm = null;
	
	private static ArrayList<Star> starsArray = new ArrayList<>();
	
	
	public static double superSpeed=1;
	private static double minSpeed=0.3*superSpeed;
	private static double maxSpeed=25*superSpeed;
	
	public Star(){
		this.centerX=(int) (0+Math.random()*gbWidth);
		this.centerY=(int) (0+Math.random()*gbHeight);
		this.yVel=getStarRanSpeed();
		
		setStarim(this);
	}
	public Star(int y){
		this.centerX=(int) (0+Math.random()*gbWidth);
		this.centerY=y;
		this.yVel=getStarRanSpeed();
		
		setStarim(this);
	}
	public void changePos(double yVel){
		this.centerY+=yVel*superSpeed;
	}
	
	public void move(){
			this.changePos(this.yVel);
			if (this.centerY>gbHeight) {
				starCount++;
				Score.incScoreBackground(1);
				this.centerY=0;
					this.yVel=getStarRanSpeed();
					this.centerX=(int) (0+Math.random()*gbWidth);
					setStarim(this);
			}
	}

	public static double getStarRanSpeed() {
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
			this.starIm = TextureHandler.getInstance().getStar1File();
		}
		else if (this.yVel > maxSpeed*0.7 && this.yVel < maxSpeed*0.95) {
			this.starIm = TextureHandler.getInstance().getStar2File();
		}
		else if (this.yVel > maxSpeed*0.5 && this.yVel < maxSpeed*0.7) {
			this.starIm = TextureHandler.getInstance().getStar3File();
		}
		else if (this.yVel > maxSpeed*0.2 && this.yVel < maxSpeed*0.5) {
			this.starIm = TextureHandler.getInstance().getStar4File();
		}
		else{
			this.starIm = TextureHandler.getInstance().getStar5File();
		}
		
	}

	public static double getSuperSpeed() {
		return superSpeed;
	}

	public static void setSuperSpeed(double superSpeed) {
		Star.superSpeed = superSpeed;
	}
	public static void createStars(int numOfStars){
		for (int i = 0; i < numOfStars; i++) {
			starsArray.add(new Star());
		}
	}
	public static void updateStars(){
		for (Star star : starsArray) {
			star.move();
			TextureHandler.drawTexture(star.starIm, star.getCenterX(), star.centerY);
		}
	}

	public static int getStarCount() {
		return starCount;
	}
	public static void addNewStar(){
		starsArray.add(new Star(0));
	}
	public static ArrayList<Star> getStarArray(){
		 return starsArray;
	}

}
