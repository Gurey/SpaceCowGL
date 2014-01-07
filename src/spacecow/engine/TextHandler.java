package spacecow.engine;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import spacecow.buffs.Magnet;
import spacecow.buffs.Rush;
import spacecow.buffs.SuperSpeed;
import spacecow.objects.Star;

public class TextHandler {

	SuperSpeed superSpeed;
	FPS fps;
	Time time;
	Score score;
	ArrayList<Star>starsArray;
	GameOver gOver;
	Magnet magnet;

	DrawText drawNormal;
	DrawText drawCenter;
	DrawText drawTime;

	public TextHandler(Game game){
		this.superSpeed=game.getSuperSpeed();
		this.fps=game.getFps();
		this.time=game.getTime();
		this.score = game.getScore();
		this.starsArray=game.getGameObjHandler().getStarsArray();
		this.gOver = game.getgOver();
		this.magnet = game.getMagnet();
		this.drawNormal=new DrawText(35,false);
		this.drawCenter=new DrawText(55, true);
		this.drawTime=new DrawText(30, true);

	}

	private void drawScore() {
		drawNormal.drawString(20, 15, String.format("Score: %,d", score.getScore()), Color.white);
	}
	//Draws rush in green if its available for use, else gray.
	private void drawRush(){
		if (Rush.getInstance().isAvailable()) {
			drawNormal.drawString(20, 45, "Rush", Color.green);
		}
		else {
			drawNormal.drawString(20, 45, "Rush", Color.gray);
		}
	}
	//Draw superSpeed in green if its available for use, else gray.
	private void drawSuperSpeed(){
		if (superSpeed.isAvailable()) {
			drawNormal.drawString(20, 75, "Super Speed", Color.green);
		}
		else {
			drawNormal.drawString(20, 75, "Super Speed", Color.gray);
		}
	}
	private void drawFPS(){
		drawNormal.drawString(Game.dWidth-170, 15, fps.getFPS(), Color.white);
	}
	private void drawScoreMulti(){
		drawNormal.drawString(20, 105, "Score multi: "+score.getScoreMulti(), Color.white);
	}
	private void drawStarNr(){
		drawNormal.drawString(20, 135, "Stars: "+starsArray.size(),Color.white);
	}
	//Draw time left in white if its more than 5 seconds left until time is up, if less its print in the time left in red.
	private  void drawTimeLeft(){
		if (time.getSecondsLeft()<5){
			drawCenter.drawString(Game.dWidth/2, 15, "Time left: "+time.getSecondsLeft()+"sec",Color.red);
		}
		else {
			drawCenter.drawString(Game.dWidth/2, 15, "Time left: "+time.getSecondsLeft()+"sec",Color.white);
		}
	}
	private void drawMagnetTimeLeft(){
		drawCenter.drawString((float)Game.dWidth/2, Game.dHeight-100, ""+(magnet.getTimeLeft()+1), Color.white);
	}
	//Printing out "Game Over!" in red and centered and the final score in white also centered.
	private void drawGameOver(){
		drawCenter.drawString((float)Game.dWidth/2, (float)(Game.dHeight/2f)-25,"Game Over!",  Color.red);
		drawCenter.drawString((float)Game.dWidth/2, (float)(Game.dHeight/2)+25f,String.format("Score: %,d", gOver.getFinalScore()), Color.white);
	}
	//updates the text in the Game phase,
	public void updateGame(){
		drawScore();
		drawRush();
		drawSuperSpeed();
		drawFPS();
		drawScoreMulti();
		drawStarNr();
		drawTimeLeft();
		if (!magnet.isAvailable()) drawMagnetTimeLeft();
		Color.white.bind();
	}
	//Updated the text in the GameOver phase.
	public void updateGameOver(){
		drawGameOver();
		Color.white.bind();
	}
}
