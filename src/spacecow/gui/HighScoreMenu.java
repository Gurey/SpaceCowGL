package spacecow.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import spacecow.engine.DrawText;
import spacecow.engine.DrawText.Alignment;
import spacecow.engine.Game;
import spacecow.engine.HighScore;

public class HighScoreMenu {

	private DrawText scoreText;
	private DrawText nameText;
	private ArrayList<HighScore> highScores;
	
	public HighScoreMenu(ArrayList<HighScore> highScores){
		this.setHighScores(highScores);
		scoreText = new DrawText(30, Alignment.RIGHT);
		setNameText(new DrawText(30, Alignment.LEFT));
	}
	public void printScore(){
		float pad = 100;
		for (HighScore hs : highScores) {
			scoreText.drawString(Game.dWidth-100, pad, String.format("%,d", hs.getScore()), Color.white);
			nameText.drawString(Game.dWidth/2, pad, (highScores.indexOf(hs)+1)+". "+hs.getName(), Color.white);
			pad+=40;
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

	public ArrayList<HighScore> getHighScores() {
		return highScores;
	}

	public void setHighScores(ArrayList<HighScore> highScores) {
		this.highScores = highScores;
	}
	
}
