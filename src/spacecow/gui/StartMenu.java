package spacecow.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameState;
import spacecow.engine.GameState.Status;
import spacecow.engine.HighScore;
import spacecow.engine.Pointer;
import spacecow.engine.TextureHandler;
import spacecow.objects.Star;

public class StartMenu {

	private ArrayList<Star> starArray;
	private ArrayList<HighScore> highScores;
	private TextureHandler texHandler;
	private Texture startGameTex, highScoresTex, optionsTex;
	private GameState gameState;
	private HighScoreMenu highScoreMenu;
	private Pointer pointer;
	
	public StartMenu(ArrayList<Star> starArray, TextureHandler texHandler, GameState gameState, ArrayList<HighScore> highScores){
		this.starArray = starArray;
		this.texHandler = texHandler;
		this.startGameTex = texHandler.getStartGameTex();
		this.highScoresTex = texHandler.getHighScoreTex();
		this.optionsTex = texHandler.getOptionsTex();
		this.gameState=gameState;
		this.highScores = highScores;
		this.highScoreMenu = new HighScoreMenu(this.highScores);
		this.pointer = new Pointer(40,100, 100, 4, 1, texHandler);
	}
	
	public void update(){
		for (Star star : starArray) {
			star.move();
		}
		texHandler.drawTexture(startGameTex, 100, 100);
		texHandler.drawTexture(highScoresTex, 100, 200);
		texHandler.drawTexture(optionsTex, 100, 300);
		pointer.updatePointerState();
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
}
