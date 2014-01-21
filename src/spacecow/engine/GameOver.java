package spacecow.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import spacecow.engine.DrawText.Alignment;
import spacecow.engine.GameState.Status;
import spacecow.objects.Star;

public class GameOver {

	ArrayList<Star> starsArray;
	private long finalScore;
	private boolean keyReleased, scoreSubmited;
	private String playerName, stringToPrint;
	private DrawText userInput;
	private ArrayList<HighScore> highScores;
	private GameState gameState;

	public GameOver(ArrayList<Star> starsArray, ArrayList<HighScore> highScores, GameState gameState){
		this.gameState = gameState;
		this.highScores = highScores;
		this.starsArray=starsArray;
		this.keyReleased = true;
		this.playerName = "";
		this.userInput = new DrawText(40, Alignment.CENTER);
	}
	//Moves the stars in the background.
	public void update(){
		for (Star star : starsArray) {
			star.move();
		}
		getInput();
		stringToPrint = "Enter Name: "+playerName+" <";

		userInput.drawString(Game.dWidth/2, Game.dHeight-100, stringToPrint, Color.white);
		submitScore();
	}

	public long getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(long score){
		this.finalScore = score;
	}
	public void getInput(){
		char keyPressed;
		if (keyReleased && Keyboard.getEventKeyState() && !Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			keyPressed = Keyboard.getEventCharacter();
			keyReleased = false;
			if (userInput.canPrint(keyPressed) && !(playerName.length()>=12)) {
				playerName += keyPressed; 
				System.out.print(keyPressed);
			}

		}
		else if (keyReleased && Keyboard.getEventKeyState() && Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			keyReleased = false;
			if (!playerName.isEmpty()) {				
				playerName = playerName.substring(0, playerName.length()-1);
			}
		}
		else if (Keyboard.next()) {
			keyReleased = true;
		}
		playerName = playerName.trim();
	}
	public void submitScore(){
		if (!playerName.equals("") && playerName.length()>=3 && Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !scoreSubmited ) {
			highScores.add(new HighScore(finalScore, new Date(), playerName));
			scoreSubmited = true;
		}
		if (scoreSubmited && !Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			gameState.setStatus(Status.MENU);
			scoreSubmited = false;
			Collections.sort(highScores);
		}
	}
	

}
