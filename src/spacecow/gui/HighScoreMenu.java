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

	private DrawText scoreText, nameText, menuText, dateText;
	private Json[] top10, personalTop10, bestAvg;
	private ArrayList<Star> starAr;
	private GameState gameState;
	private Pointer pointer;
	private boolean enterKeyPressed;
	private float xPos;

	public HighScoreMenu(ArrayList<Star> starArray, TextureHandler texHandler, GameState gameState){
		this.starAr = starArray;
		this.gameState = gameState;
		this.xPos = (Game.dWidth/2);
		this.top10 = new Json[0];
		this.personalTop10 =  new Json[0];
		this.bestAvg = new Json[0];
		menuText = new DrawText(40, Alignment.LEFT);
		scoreText = new DrawText(35, Alignment.RIGHT);
		setNameText(new DrawText(35, Alignment.LEFT));
		this.dateText = new DrawText(35, Alignment.LEFT);
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
			printScore(personalTop10, "Personal Top Scores");
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
		int rank = 1;
		nameText.drawString((Game.dWidth/2)-200, 50, title, Color.white);
		if (jsonArray.length>0) {	
			for (Json json : jsonArray) {
				if (json.getName().isEmpty()) return; 
				scoreText.drawString(Game.dWidth-200, pad, String.format("%,d", json.getScore()), Color.white);
				dateText.drawString(xPos+50, pad, ""+json.getDate(), Color.white);
				if (!jsonArray.equals(personalTop10))
					nameText.drawString((Game.dWidth/2)-200, pad, rank+". "+json.getName(), Color.white);
				else nameText.drawString((Game.dWidth/2)-200, pad, json.getName()+".", Color.white);
				rank++;
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
