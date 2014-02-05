package spacecow.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import spacecow.engine.DrawText;
import spacecow.engine.GameState;
import spacecow.engine.DrawText.Alignment;
import spacecow.engine.GameState.Status;
import spacecow.engine.Game;
import spacecow.engine.HighScore;
import spacecow.engine.Pointer;
import spacecow.engine.TextureHandler;
import spacecow.objects.Star;
import spacecow.serverconnection.Json;

public class StartMenu {

	private ArrayList<Star> starArray;
	private ArrayList<HighScore> highScores;
	private TextureHandler texHandler;
	private Texture startGameTex, highScoresTex, optionsTex;
	private GameState gameState;
	private HighScoreMenu highScoreMenu;
	private Pointer pointer;
	private Json playerStats;
	private DrawText statText;
	private DrawText statVal;
	
	public StartMenu(ArrayList<Star> starArray, TextureHandler texHandler, GameState gameState, ArrayList<HighScore> highScores){
		this.playerStats = new Json();
		this.starArray = starArray;
		this.texHandler = texHandler;
		this.startGameTex = texHandler.getStartGameTex();
		this.highScoresTex = texHandler.getHighScoreTex();
		this.optionsTex = texHandler.getOptionsTex();
		this.gameState=gameState;
		this.highScores = highScores;
		this.highScoreMenu = new HighScoreMenu(this.highScores);
		this.pointer = new Pointer(40,100, 100, 4, 1, texHandler);
		this.statText = new DrawText(35, Alignment.LEFT);
		this.statVal = new DrawText(35, Alignment.RIGHT);
	}
	
	public void update(){
		for (Star star : starArray) {
			star.move();
		}
		texHandler.drawTexture(startGameTex, 100, 100);
		texHandler.drawTexture(highScoresTex, 100, 200);
		texHandler.drawTexture(optionsTex, 100, 300);
		pointer.updatePointerState();
		
		if (pointer.getPointerState()==1 && gameState.getStatus() == Status.MENU) {
			printStats();
		}
		checkIfExe();
		if (gameState.getStatus()==Status.HIGHSCORE) {
			highScoreMenu.printScore();
		}
	}
	public void checkIfExe(){
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
		switch (pointer.getPointerState()) {
		case 1:
			gameState.setStatus(Status.STARTGAME);
			break;
			
		case 2:
			gameState.setStatus(Status.HIGHSCORE);
			break;
			
		case 3:
			gameState.setStatus(Status.OPTIONS);
			break;
		case 4:
			gameState.setStatus(Status.EXIT);
		default:
			break;
		}
		}
	}
	
	public void printStats(){
		float pad = 60;
		float xPos = (Game.dWidth/2)-100;
		float yPos = 100;
			statVal.drawString(Game.dWidth-100, yPos, String.format("%,d", playerStats.getScore()), Color.white);
			statText.drawString(xPos, yPos, "Total score:", Color.white);
			yPos+=pad;
			statVal.drawString(Game.dWidth-100, yPos, String.format("%,d", playerStats.getAvgScore()), Color.white);
			statText.drawString(xPos, yPos, "Average score:", Color.white);
			yPos+=pad;
			statVal.drawString(Game.dWidth-100, yPos, ""+playerStats.getTime()+"sec", Color.white);
			statText.drawString(xPos, yPos, "Total time played:", Color.white);
			yPos+=pad;
			statVal.drawString(Game.dWidth-100, yPos, ""+playerStats.getStars(), Color.white);
			statText.drawString(xPos, yPos, "Total Stars catched:", Color.white);
			yPos+=pad;
			statVal.drawString(Game.dWidth-100, yPos, ""+playerStats.getMulti(), Color.white);
			statText.drawString(xPos, yPos, "Total Multis catched:", Color.white);
			yPos+=pad;
			statVal.drawString(Game.dWidth-100, yPos, ""+playerStats.getCookies(), Color.white);
			statText.drawString(xPos, yPos, "Total Cookies catched:", Color.white);
			yPos+=pad;
			statVal.drawString(Game.dWidth-100, yPos, ""+playerStats.getMagnets(), Color.white);
			statText.drawString(xPos, yPos, "Total Magnets catched:", Color.white);
			yPos+=pad;
			statVal.drawString(Game.dWidth-100, yPos, ""+playerStats.getAstoids(), Color.white);
			statText.drawString(xPos, yPos, "Total Asterdoid collisions:", Color.white);
	}

	public ArrayList<Star> getStarArray() {
		return starArray;
	}

	public TextureHandler getTexHandler() {
		return texHandler;
	}

	public Texture getStartGame() {
		return startGameTex;
	}

	public Texture getHighScores() {
		return highScoresTex;
	}

	public Texture getOptions() {
		return optionsTex;
	}

	public GameState getGameState() {
		return gameState;
	}

	public Json getPlayerStats() {
		return playerStats;
	}

	public void setPlayerStats(Json playerStats) {
		this.playerStats = playerStats;
	}
}
