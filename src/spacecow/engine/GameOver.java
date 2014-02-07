package spacecow.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.google.gson.Gson;

import spacecow.engine.DrawText.Alignment;
import spacecow.engine.GameState.Status;
import spacecow.objects.Star;
import spacecow.serverconnection.Json;
import spacecow.serverconnection.ServerConnection;

public class GameOver {

	ArrayList<Star> starsArray;
	private long finalScore;
	private boolean scoreSubmited, enterKeyPressed;
	private String stringToPrint;
	private DrawText drawStats;
	private GameState gameState;
	private Score score;
	private long duration;
	private ServerConnection connection;

	public GameOver(ArrayList<Star> starsArray, GameState gameState, Score score, ServerConnection connection){
		this.connection = connection;
		this.score = score;
		this.gameState = gameState;
		this.starsArray=starsArray;
		this.scoreSubmited = false;
		this.drawStats = new DrawText(30, Alignment.CENTER);
	}
	//Moves the stars in the background.
	public void update(){
		for (Star star : starsArray) {
			star.move();
		}
		
		stringToPrint = "| Stars: "+score.getStarCol()
				+" | Multi: "+score.getMultiCol()
				+" | Cookies: "+score.getCookiaCol()
				+" | Magnets: "+score.getMagnetCol()
				+" | Asteroids: "+score.getAstroidCol()
				+" | Duration: "+(duration/Sys.getTimerResolution()+1)
				+" |";
		
		drawStats.drawString(Game.dWidth/2, Game.dHeight-100, stringToPrint, Color.white);
		submitScore();
		checkEnter();
	}
	public void submitScore(){
		if (!scoreSubmited) {
			Json scoreToSubmit = new Json();
			scoreToSubmit.setType("NEWSCORE");
			scoreToSubmit.setScore(finalScore);
			scoreToSubmit.setStars(score.getStarCol());
			scoreToSubmit.setMulti(score.getMultiCol());
			scoreToSubmit.setCookies(score.getCookiaCol());
			scoreToSubmit.setMagnets(score.getMagnetCol());
			scoreToSubmit.setAstoids(score.getAstroidCol());
			scoreToSubmit.setTime((int) ((duration/Sys.getTimerResolution())+1));
			connection.send(new Gson().toJson(scoreToSubmit, Json.class));
			scoreSubmited = true;
		}
	}
	public void checkEnter(){
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			enterKeyPressed = true;
		}
		if (scoreSubmited && !Keyboard.isKeyDown(Keyboard.KEY_RETURN) && enterKeyPressed) {
			gameState.setStatus(Status.MENU);
			scoreSubmited = false;
			enterKeyPressed = false;
		}
	}
	
	public boolean isScoreSubmited() {
		return scoreSubmited;
	}
	public void setScoreSubmited(boolean scoreSubmited) {
		this.scoreSubmited = scoreSubmited;
	}
	public long getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(long score){
		this.finalScore = score;
	}
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long startTime) {
		this.duration = startTime;
	}
	

}
