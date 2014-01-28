package spacecow.engine;


import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import spacecow.buffs.Magnet;
import spacecow.buffs.Rush;
import spacecow.engine.GameState.Status;
import spacecow.gui.CreateNewAccountMenu;
import spacecow.gui.LogonMenu;
import spacecow.gui.StartMenu;
import spacecow.objects.GameObjectHandler;
import spacecow.objects.Player;

public class Game {
	
	public static int dWidth = Display.getDesktopDisplayMode().getWidth();
	public static int dHeight = Display.getDesktopDisplayMode().getHeight();
	
	private Score score;
	private GameObjectHandler gameObjHandler;
	private Magnet magnet;
	private TextureHandler texHandler;
	private Player player;
	private FPS fps;
	private GameOver gOver;
	private Time time;
	private Rush rush;
	private GameState gameState;
	private StartMenu startMenu;
	private CountDown count;
	private ArrayList<HighScore> highScoreArray;
	private DisplayConfig dConfig;
	private LogonMenu logonMenu;
	private CreateNewAccountMenu createNew;
	
	TextHandler textHandler;

	public Game(){
		initGL();
		setFps(new FPS());
		this.highScoreArray = new ArrayList<>();
		gameState = new GameState();
		rush = new Rush();
		time = new Time();
		texHandler = new TextureHandler();
		score = new Score();
		player = new Player(texHandler, rush);
		gameObjHandler = new GameObjectHandler(score, texHandler, player, time);
		magnet = new Magnet(gameObjHandler.getGameObjectArray(), player);
		gOver = new GameOver(gameObjHandler.getStarsArray(), highScoreArray, gameState);
		gameObjHandler.setMagnet(magnet);
		startMenu = new StartMenu(gameObjHandler.getStarsArray(), texHandler, gameState, highScoreArray);
		count = new CountDown(gameObjHandler.getStarsArray(), texHandler);
		textHandler = new TextHandler(this);
		dConfig = new DisplayConfig();
		logonMenu = new LogonMenu(gameObjHandler.getStarsArray(), texHandler, gameState);
		createNew = new CreateNewAccountMenu(gameObjHandler.getStarsArray(), texHandler, gameState);
	}
	
	//Set up the display and create it.
	public void initGL(){
		try {
			Display.setDisplayMode(new DisplayMode(dWidth, dHeight));
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		glViewport(0, 0, dWidth, dHeight);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		glEnable(GL_TEXTURE_2D);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		//Ortho is the dimentions of the game in x, y and z axis
		glOrtho(0, dWidth, dHeight, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
	}
	
	public void start(){
		dConfig.setDisplayMode(dWidth, dHeight, !Display.isFullscreen());
		gameState.setStatus(Status.LOGON);
		while (!Display.isCloseRequested() && !gameState.getStatus().equals(Status.EXIT)) {
		while (!Display.isCloseRequested() && (gameState.getStatus()==Status.LOGON || gameState.getStatus()==Status.CREATENEW)){
			render();
			if (gameState.getStatus()==Status.LOGON) logonMenu.update();
			else createNew.update();
			Display.update();
			Display.sync(60);
			
		}
		while (!(gameState.getStatus()==Status.STARTGAME)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				dConfig.setDisplayMode(dWidth, dHeight, !Display.isFullscreen());
			}
			render();
			startMenu.update();
			Display.update();
			Display.sync(60);
			if (gameState.getStatus().equals(Status.EXIT) || Display.isCloseRequested()){
				Display.destroy();
				return;
			}
		}
		//Starts the CoundDown of the game, exits when countdown is over.
		count.setCountDownState(3);
		while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && gameState.getStatus().equals(Status.STARTGAME)) {
			render();
			if (count.getCountdownState()<=0) {
				break;
			}
			player.update();
			rush.update();
			count.countDown();
			Display.update();
			Display.sync(60);
		}
		//Sets the Score to 0 and the time left to XX seconds.
		score.setScore(0);
		time.setTimeLeft(60);
		rush.resetRush();
		//init the Game, running until the Player press Esc or the time runs out.
		while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !(time.getSecondsLeft()<=0) && gameState.getStatus().equals(Status.STARTGAME)) {
			render();
			update();
			Display.update();
			Display.sync(60);
		}
		//Sets the finalscore to the current score.
		gOver.setFinalScore(score.getScore());
		//Run gameover until the players wants to exit.
		Keyboard.destroy();
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && time.getSecondsLeft()<=0 && gameState.getStatus().equals(Status.STARTGAME)) {
			render();
			gOver.update();
			textHandler.updateGameOver();
			Display.update();
			Display.sync(60);
		}
		resetGame();
		}
		Display.destroy();
	}

	private void resetGame() {
		this.gameState.setStatus(Status.MENU);
		this.gameObjHandler.getGameObjectArray().clear();
		this.score.setScoreMulti(1);
		while (gameObjHandler.getStarsArray().size()>gameObjHandler.getNumberOfStars()) {
			gameObjHandler.getStarsArray().remove(gameObjHandler.getStarsArray().size()-1);
		}
	}
	//updates all components in the Game phase.
	public void update(){
		gameObjHandler.update();
		rush.update();
		player.update();
		magnet.update();
		score.update();
		textHandler.updateGame();
	}
	//Prints out the content on the screen.
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	public Score getScore() {
		return score;
	}
	public GameObjectHandler getGameObjHandler() {
		return gameObjHandler;
	}
	public Magnet getMagnet() {
		return magnet;
	}
	public TextureHandler getTexHandler() {
		return texHandler;
	}
	public Player getPlayer() {
		return player;
	}
	public GameOver getgOver() {
		return gOver;
	}
	public Time getTime() {
		return time;
	}
	public FPS getFps() {
		return fps;
	}
	public void setFps(FPS fps) {
		this.fps = fps;
	}
	public Rush getRush(){
		return rush;
	}
	public GameState getGameState() {
		return gameState;
	}
	public StartMenu getStartMenu() {
		return startMenu;
	}

	public ArrayList<HighScore> getHighScoreArray() {
		return highScoreArray;
	}

	public void setHighScoreArray(ArrayList<HighScore> highScoreArray) {
		this.highScoreArray = highScoreArray;
	}
}
