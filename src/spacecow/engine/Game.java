package spacecow.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import spacecow.buffs.Magnet;
import spacecow.buffs.SuperSpeed;
import spacecow.objects.GameObjectHandler;
import spacecow.objects.Player;
import static org.lwjgl.opengl.GL11.*;

public class Game {
	
	public static int dWidth = 1400;
	public static int dHeight = 750;
	
	private Score score;
	private GameObjectHandler gameObjHandler;
	private Magnet magnet;
	private TextureHandler texHandler;
	private Player player;
	private SuperSpeed superSpeed;
	private FPS fps;
	private GameOver gOver;
	private Time time = new Time();
	
	TextHandler textHandler;

	public Game(){
		initGL();
		setFps(new FPS());
		superSpeed = new SuperSpeed();
		texHandler = new TextureHandler();
		score = new Score();
		player = new Player(texHandler, superSpeed);
		gameObjHandler = new GameObjectHandler(score, texHandler, superSpeed, player);
		magnet = new Magnet(gameObjHandler.getGameObjectArray(), player);
		gOver = new GameOver(gameObjHandler.getStarsArray());
		gameObjHandler.setMagnet(magnet);
		textHandler = new TextHandler(this);
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
		CountDown count = new CountDown(gameObjHandler.getStarsArray(), texHandler);
		//Starts the CoundDown of the game, exits when countdown is over.
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			render();
			if (count.getCountdownState()<=0) {
				break;
			}
			player.update();
			count.countDown();
			Display.update();
			Display.sync(60);
		}
		//Sets the Score to 0 and the time left to XX seconds.
		score.setScore(0);
		time.setTimeLeft(60);
		//init the Game, running until the Player press Esc or the time runs out.
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !(time.getSecondsLeft()<=0)) {
			render();
			update();
			Display.update();
			Display.sync(60);
		}
		//Sets the finalscore to the current score.
		gOver.setFinalScore(score.getScore());
		//Run gameover until the players wants to exit.
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			render();
			gOver.update();
			textHandler.updateGameOver();
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
	//updates all components in the Game phase.
	public void update(){
		gameObjHandler.update();
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
	public SuperSpeed getSuperSpeed() {
		return superSpeed;
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
}
