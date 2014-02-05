package spacecow.gui;


import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import spacecow.engine.DrawText;
import spacecow.engine.GameState;
import spacecow.engine.Pointer;
import spacecow.engine.TextureHandler;
import spacecow.engine.DrawText.Alignment;
import spacecow.engine.GameState.Status;
import spacecow.engine.Game;
import spacecow.objects.Star;
import spacecow.serverconnection.Json;

public class HighScoreMenu {

	private DrawText scoreText, nameText, menuText;
	private Json[] top10, personalTop10, bestAvg;
	private ArrayList<Star> starAr;
	private TextureHandler textureHandler;
	private GameState gameState;
	private Pointer pointer;
	private boolean enterKeyPressed;

	public HighScoreMenu(ArrayList<Star> starArray, TextureHandler texHandler, GameState gameState){
		this.starAr = starArray;
		this.textureHandler = texHandler;
		this.gameState = gameState;
		this.top10 = new Json[0];
		this.personalTop10 =  new Json[0];
		this.bestAvg = new Json[0];
		menuText = new DrawText(40, Alignment.LEFT);
		scoreText = new DrawText(35, Alignment.RIGHT);
		setNameText(new DrawText(35, Alignment.LEFT));
		this.pointer = new Pointer(40,100, 100, 4, 1, texHandler);
	}

	public void update(){
		for (Star star : starAr) {
			star.move();
		}
		pointer.updatePointerState();
		menuText.drawString(100, 100, "Top 10", Color.white);
		menuText.drawString(100, 200, "Your top 10", Color.white);
		menuText.drawString(100, 300, "Best avg. score", Color.white);
		menuText.drawString(100, 400, "<- Back", Color.white);
		switch (pointer.getPointerState()) {
		case 1:
			printScore(top10, "Top 10");
			break;
		case 2:
			printScore(personalTop10, "Personal Top 10");
			break;
		case 3:
			printScore(bestAvg, "Best average score");
			break;
		case 4:
			checkEnter();
			break;
		default:
			break;
		}
	}
	public void printScore(Json[] jsonArray, String title){
		float pad = 100;
		nameText.drawString((Game.dWidth/2)-100, 50, title, Color.white);
		if (jsonArray.length>0) {	
			for (Json hs : jsonArray) {
				if (hs.getName().isEmpty()) return; 
				scoreText.drawString(Game.dWidth-100, pad, String.format("%,d", hs.getScore()), Color.white);
				nameText.drawString((Game.dWidth/2)-100, pad, hs.getName(), Color.white);
				pad+=60;
			}
		}

	}
	public void checkEnter(){
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			enterKeyPressed = true;
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_RETURN) && enterKeyPressed) {
			gameState.setStatus(Status.MENU);
			enterKeyPressed = false;
			pointer.setPointerState(1);
		}
	}

	public DrawText getScoreText() {
		return scoreText;
	}

	public void setScoreText(DrawText scoreText) {
		this.scoreText = scoreText;
	}

	public DrawText getNameText() {
		return nameText;
	}

	public void setNameText(DrawText nameText) {
		this.nameText = nameText;
	}
	public Json[] getHighscore() {
		return top10;
	}
	public void setHighscore(Json[] highscore) {
		this.top10 = highscore;
	}

	public Json[] getPersonalTop10() {
		return personalTop10;
	}

	public Json[] getBestAvg() {
		return bestAvg;
	}

	public void setPersonalTop10(Json[] personalTop10) {
		this.personalTop10 = personalTop10;
	}

	public void setBestAvg(Json[] bestAvg) {
		this.bestAvg = bestAvg;
	}
}
