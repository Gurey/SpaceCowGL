package spacecow.engine;

import java.awt.Font;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import spacecow.buffs.Rush;
import spacecow.buffs.SuperSpeed;
import spacecow.objects.Player;
import spacecow.objects.ScoreMultiplyer;
import spacecow.objects.Star;

public class DrawText {

	static TrueTypeFont font;
	
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
		font.drawString(20, 135, "Stars: "+Star.getStarArray().size());
	}
	public static void drawPoints(){
//		DrawPoints.getDrawPointsArray().add((new DrawPoints(font, Player.getInstance().getX(), Player.getInstance().getY(), ""+Score.getLatestScore())));
		}
	public static void update(){
		for (DrawPoints dpR : DrawPoints.getRemovePointsArray()) {
			DrawPoints.getDrawPointsArray().remove(dpR);
		}
		drawScore();
		drawRush();
		drawSuperSpeed();
		drawFPS();
		drawScoreMulti();
		drawStarNr();
		Color.white.bind();
		for (DrawPoints drawPoints : DrawPoints.getDrawPointsArray()) {
			drawPoints.update();
		}
		Color.white.bind();
	}
	
	
	
	
	
	
	
	
	
}
