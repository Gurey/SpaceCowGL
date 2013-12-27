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
	private IterationTimer iTimer = new IterationTimer();
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
		textHandler = new TextHandler(this);
	}
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
		
		glOrtho(0, dWidth, dHeight, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		
	}
	
	public void start(){
//		CountDown count = new CountDown(gameObjHandler.getStarsArray(), texHandler);
//		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
//			render();
//			if (count.getCountdownState()<=0) {
//				break;
//			}
//			player.update();
//			count.countDown();
//			Display.update();
//			Display.sync(60);
//		}
		iTimer.refreshTime();
		score.setScore(0);
		time.setTimeLeft(60);
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !(time.getSecondsLeft()<=0)) {
			iTimer.startTimer();
			render();
			update();
			Display.update();
			Display.sync(60);
			iTimer.getAverageIterationTime();
		}
		gOver.setFinalScore(score.getScore());
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			render();
			gOver.update();
			textHandler.updateGameOver();
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
	public void update(){
		gameObjHandler.update();
		player.update();
		magnet.update();
		score.update();
		textHandler.updateGame();
	}
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
	public IterationTimer getiTimer() {
		return iTimer;
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
