package spacecow.engine;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import spacecow.buffs.Rush;
import spacecow.buffs.SuperSpeed;
import spacecow.objects.GameObjectHandler;

public class DrawText {

	static TrueTypeFont font;
	
	private static ArrayList<DrawPoints> drawPointsArray = new ArrayList<>();
	private static ArrayList<DrawPoints> removePointsArray = new ArrayList<>();
	
	static Time time = new Time();

	public static void initText(){
		Font score = new Font("Bank Gothic", Font.BOLD, 35);
		font = new TrueTypeFont(score, false);
	}
	
	private static void drawScore(){
		font.drawString(20, 15, ""+Score.getScore(), Color.white);
	}
	private static void drawRush(){
		if (Rush.getInstance().isAvailable()) {
			font.drawString(20, 45, "Rush", Color.green);
		}
		else {
			font.drawString(20, 45, "Rush", Color.gray);
		}
	}
	private static void drawSuperSpeed(){
		if (SuperSpeed.getInstance().isAvailable()) {
			font.drawString(20, 75, "Super Speed", Color.green);
		}
		else {
			font.drawString(20, 75, "Super Speed", Color.gray);
		}
	}
	private static void drawFPS(){
		font.drawString(Game.dWidth-170, 15, FPS.getFPS(), Color.white);
	}
	private static void drawScoreMulti(){
		font.drawString(20, 105, "Score multi: "+Score.getScoreMulti());
	}
	private static void drawStarNr(){
		font.drawString(20, 135, "Stars: "+GameObjectHandler.getInstance().getStarsArray().size());
	}
	private static void drawTimeLeft(){
		if (time.getSecondsLeft()<5){
		font.drawString(Game.dWidth/2, 15, "Time left: "+time.getSecondsLeft()+"sec",Color.red);
		}
		else {
		font.drawString(Game.dWidth/2, 15, "Time left: "+time.getSecondsLeft()+"sec",Color.white);
		}
	}
	public static void drawPoints(){
//		DrawPoints.getDrawPointsArray().add((new DrawPoints(font, Player.getInstance().getX(), Player.getInstance().getY(), ""+Score.getLastestScore())));
		}
	public static void update(){
		for (DrawPoints dpR : DrawText.getRemovePointsArray()) {
			DrawText.getDrawPointsArray().remove(dpR);
		}
		drawScore();
		drawRush();
		drawSuperSpeed();
		drawFPS();
		drawScoreMulti();
		drawStarNr();
		drawTimeLeft();
		Color.white.bind();
		for (DrawPoints drawPoints : DrawText.getDrawPointsArray()) {
			drawPoints.update();
		}
		Color.white.bind();
	}
	
	public static ArrayList<DrawPoints> getDrawPointsArray() {
		return drawPointsArray;
	}
	
	public static ArrayList<DrawPoints> getRemovePointsArray() {
		return removePointsArray;
	}
}
