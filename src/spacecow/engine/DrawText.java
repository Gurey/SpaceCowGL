package spacecow.engine;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;


public class DrawText {

	private TrueTypeFont font;
	
	public DrawText(int size){
		Font score = new Font("Bank Gothic", Font.BOLD, size);
		font = new TrueTypeFont(score, false);
	}
	public void drawString(String text, float x, float y, Color color, boolean isCentered){
		if (isCentered) {
		float textWidth = (float)font.getWidth(text)/2;
		font.drawString(x - textWidth, y, text, color);
		}
		else{
		font.drawString(x, y, text, color);
		}
	}
	private void drawScore() {
		
	}
	private void drawRush(){
		if (Rush.getInstance().isAvailable()) {
			font.drawString(20, 45, "Rush", Color.green);
		}
		else {
			font.drawString(20, 45, "Rush", Color.gray);
		}
	}
	private void drawSuperSpeed(){
		if (SuperSpeed.getInstance().isAvailable()) {
			font.drawString(20, 75, "Super Speed", Color.green);
		}
		else {
			font.drawString(20, 75, "Super Speed", Color.gray);
		}
	}
	private void drawFPS(){
		font.drawString(Game.dWidth-170, 15, FPS.getFPS(), Color.white);
	}
	private void drawScoreMulti(){
		font.drawString(20, 105, "Score multi: "+Score.getScoreMulti());
	}
	private void drawStarNr(){
		font.drawString(20, 135, "Stars: "+GameObjectHandler.getInstance().getStarsArray().size());
	}
	private  void drawTimeLeft(){
		if (time.getSecondsLeft()<5){
		font.drawString(Game.dWidth/2, 15, "Time left: "+time.getSecondsLeft()+"sec",Color.red);
		}
		else {
		font.drawString(Game.dWidth/2, 15, "Time left: "+time.getSecondsLeft()+"sec",Color.white);
		}
	}
	public void update(){
		drawScore();
		drawRush();
		drawSuperSpeed();
		drawFPS();
		drawScoreMulti();
		drawStarNr();
		drawTimeLeft();
		Color.white.bind();
	}
}
