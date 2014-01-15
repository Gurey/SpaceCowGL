package spacecow.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import spacecow.engine.GameState;
import spacecow.engine.GameState.Status;
import spacecow.engine.HighScore;
import spacecow.engine.TextureHandler;
import spacecow.objects.Star;

public class StartMenu {

	private ArrayList<Star> starArray;
	private TextureHandler texHandler;
	private Texture startGameTex, highScoresTex, optionsTex, pointerTex;
	private GameState gameState;
	private boolean keyRealeased;
	private int pointerState, pointerStateMax;
	private HighScoreMenu highScoreMenu;
	
	public StartMenu(ArrayList<Star> starArray, TextureHandler texHandler, GameState gameState, ArrayList<HighScore> highScores){
		this.starArray = starArray;
		this.texHandler = texHandler;
		this.startGameTex = texHandler.getStartGameTex();
		this.highScoresTex = texHandler.getHighScoreTex();
		this.optionsTex = texHandler.getOptionsTex();
		this.pointerTex = texHandler.getStarBuffTex();
		this.gameState=gameState;
		this.keyRealeased = true;
		this.pointerState = 1;
		this.pointerStateMax = 4;
		this.highScoreMenu = new HighScoreMenu(highScores);
	}
	
	public void update(){
		for (Star star : starArray) {
			star.move();
		}
		texHandler.drawTexture(startGameTex, 100, 100);
		texHandler.drawTexture(highScoresTex, 100, 200);
		texHandler.drawTexture(optionsTex, 100, 300);
		updatePointerState();
		drawPointer();
		checkIfExe();
		if (gameState.getStatus()==Status.HIGHSCORE) {
			highScoreMenu.printScore();
		}
	}
	public void updatePointerState(){
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && keyRealeased) {
			pointerState++;
			keyRealeased=false;
			if (pointerState>pointerStateMax) pointerState=pointerStateMax;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_UP)&& keyRealeased){
			pointerState--;
			keyRealeased=false;
			if(pointerState<1) pointerState=1;
		}
		else if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			keyRealeased = true;
		}
	}
	
	public void drawPointer(){
		switch (pointerState) {
		case 1:
			texHandler.drawTexture(pointerTex, 40, 100);
			break;
			
		case 2:
			texHandler.drawTexture(pointerTex, 40, 200);
			break;
			
		case 3:
			texHandler.drawTexture(pointerTex, 40, 300);
			break;
		case 4:
			texHandler.drawTexture(pointerTex, 40, 400);
			break;
		default:
			break;
		}
	}
	public void checkIfExe(){
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
		switch (pointerState) {
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

	public Texture getPointerTex() {
		return pointerTex;
	}
}
