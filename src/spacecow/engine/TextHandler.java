package spacecow.engine;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import spacecow.buffs.Rush;
import spacecow.buffs.SuperSpeed;
import spacecow.objects.Star;

public class TextHandler {
	
	//Rush rush;
	SuperSpeed superSpeed;
	FPS fps;
	Time time;
	Score score;
	ArrayList<Star>starsArray;
	GameOver gOver;
	
	DrawText drawNormal;
	DrawText drawCenter;

	public TextHandler(Game game){
		//this.rush = game.get
		this.superSpeed=game.getSuperSpeed();
		this.fps=game.getFps();
		this.time=game.getTime();
		this.score = game.getScore();
		this.starsArray=game.getGameObjHandler().getStarsArray();
		this.gOver = game.getgOver();
		this.drawNormal=new DrawText(20,false);
		this.drawCenter=new DrawText(55, true);
		
	}
	
	private void drawScore() {
	
	}
	private void drawRush(){
	if (Rush.getInstance().isAvailable()) {
		drawNormal.drawString(20, 45, "Rush", Color.green);
	}
	else {
		drawNormal.drawString(20, 45, "Rush", Color.gray);
	}
}
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
	private  void drawTimeLeft(){
	if (time.getSecondsLeft()<5){
	drawNormal.drawString(Game.dWidth/2, 15, "Time left: "+time.getSecondsLeft()+"sec",Color.red);
	}
	else {
	drawNormal.drawString(Game.dWidth/2, 15, "Time left: "+time.getSecondsLeft()+"sec",Color.white);
	}
}
	private void drawGameOver(){
		drawCenter.drawString((float)Game.dWidth/2, (float)(Game.dHeight/2f)-25,"Game Over!!",  Color.red);
		drawCenter.drawString((float)Game.dWidth/2, (float)(Game.dHeight/2)+25f,"Score: "+gOver.getFinalScore(), Color.white);
	}
	public void updateGame(){
	drawScore();
	drawRush();
	drawSuperSpeed();
	drawFPS();
	drawScoreMulti();
	drawStarNr();
	drawTimeLeft();
	Color.white.bind();
}
	public void updateGameOver(){
		drawGameOver();
		Color.white.bind();
	}
}
