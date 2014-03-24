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

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.google.gson.Gson;

import spacecow.buffs.Magnet;
import spacecow.buffs.Rush;
import spacecow.engine.GameState.Status;
import spacecow.gui.ChangePassword;
import spacecow.gui.CreateNewAccountMenu;
import spacecow.gui.HighScoreMenu;
import spacecow.gui.HowToPlay;
import spacecow.gui.LogonMenu;
import spacecow.gui.LostPassword;
import spacecow.gui.Options;
import spacecow.gui.StartMenu;
import spacecow.gui.StartScreen;
import spacecow.objects.GameObjectHandler;
import spacecow.objects.Player;
import spacecow.objects.Star;
import spacecow.serverconnection.Json;
import spacecow.serverconnection.ServerConnection;

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
	private DisplayConfig dConfig;
	private LogonMenu logonMenu;
	private CreateNewAccountMenu createNew;
	private ServerConnection serverConnection;
	private HighScoreMenu scoreMenu;
	private LostPassword lostPassword;
	private HowToPlay howToPlay;
	private Options options;
	private ChangePassword changePassword;
	private StartScreen startScreen;

	TextHandler textHandler;

	private long startTime = 0;

	public Game(){
		initGL();
		try {
			serverConnection = new ServerConnection(this);
			Thread t = new Thread(serverConnection);
			t.start();
		} catch (Exception e) {
		}
		setFps(new FPS());
		gameState = new GameState();
		rush = new Rush();
		time = new Time();
		texHandler = new TextureHandler();
		score = new Score();
		player = new Player(texHandler, rush);
		gameObjHandler = new GameObjectHandler(score, texHandler, player, time);
		magnet = new Magnet(gameObjHandler.getGameObjectArray(), player);
		gOver = new GameOver(gameObjHandler.getStarsArray(), gameState, score, serverConnection);
		gameObjHandler.setMagnet(magnet);
		count = new CountDown(gameObjHandler.getStarsArray(), texHandler);
		startMenu = new StartMenu(texHandler, gameState, count);
		textHandler = new TextHandler(this);
		setdConfig(new DisplayConfig());
		logonMenu = new LogonMenu(texHandler, gameState, serverConnection);
		createNew = new CreateNewAccountMenu(texHandler, gameState, serverConnection);
		setScoreMenu(new HighScoreMenu(texHandler, gameState));
		lostPassword = new LostPassword(texHandler, gameState, serverConnection);
		howToPlay = new HowToPlay(gameObjHandler, player, texHandler, magnet);
		options = new Options(this);
		changePassword = new ChangePassword(this);
		startScreen = new StartScreen(this);
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
		gameState.setStatus(Status.STARTSCREEN);
//		updateTopLists();
		while (!Display.isCloseRequested() && !gameState.getStatus().equals(Status.EXIT)) {
			moveStars();
			switch (gameState.getStatus()) {
			case LOGON:
				logonMenu.update();
				break;
			case STARTSCREEN:
				startScreen.runMenu();
				break;
			case MENU:
				startMenu.update();
				break;
			case COUNTDOWN:
				countDown();
				break;
			case STARTGAME:
				input();
				update();
				renderGame();
				if (time.getSecondsLeft()<=0) {
					gameState.setStatus(Status.GAMEOVER);
					gOver.setFinalScore(score.getScore());
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					gameState.setStatus(Status.MENU);
				}
				break;
			case GAMEOVER:
				gameOver();
				break;
			case HIGHSCORE:
				scoreMenu.update();
				break;
			case OPTIONS:
				options.runMenu();
				break;
			case CHANGEPASS:
				changePassword.runMenu();
				break;
			case LOSTPASSWORD:
				lostPassword.update();
				break;
			case CREATENEW:
				createNew.update();
				break;
			case HOWTOPLAY:
				howToPlay.update();
				rush.update();
				magnet.update();
				player.render();
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					gameState.setStatus(Status.MENU);
					magnet.setTimeLeft(0);
				}
				break;
			default:
				break;
			}
			Display.update();
			render();
			Display.sync(60);
		}

		if (serverConnection != null) {
			serverConnection.closeAllConnections();
			System.out.println("Connections Closed!");
		}
		Display.destroy();
	}

	private void input(){
		player.update();
	}

	//updates all components in the Game phase.
	public void update(){
			gameUpdate();
	}

	private void gameUpdate() {
		gameObjHandler.update();
		rush.update();
		magnet.update();
		score.update();
	}
	
	private void renderGame(){
		player.render();
		textHandler.updateGame();
		
	}

	//Prints out the content on the screen.
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void countDown() {
		//Starts the CoundDown of the game, exits when countdown is over.
		if (!isEscPressed()) {
			if (count.getCountdownState()<=0) {
				//Sets the Score to 0 and the time left to XX seconds.
				score.setScore(0);
				time.setTimeLeft(60);
				startTime = Sys.getTime();
				rush.resetRush();
				gameObjHandler.resetObjectTimers();
				resetGame();
				gameState.setStatus(Status.STARTGAME);
			}
			player.update();
			rush.update();
			magnet.update();
			player.render();
			count.countDown();
		}
	}

	public void checkIFReset() {
		if (time.getSecondsLeft()<=0) {
			resetGame();
			System.out.println("Game reset");
		}
	}

	public void gameOver() {
			gOver.update();
			textHandler.updateGameOver();
	}

	public void configGameOver() {
		if (time.getSecondsLeft()<=0 && gameState.getStatus()==Status.STARTGAME) {
			//Sets the finalscore to the current score.
			gOver.setFinalScore(score.getScore());
			//Run gameover until the players wants to exit.
			gOver.setDuration(Sys.getTime()-startTime);
			gameState.setStatus(Status.GAMEOVER);
		}
	}

	public void runGame() {
		//init the Game, running until the Player press Esc or the time runs out.
		if (!isEscPressed() && !(time.getSecondsLeft()<=0) && gameState.getStatus().equals(Status.STARTGAME)) {
			update();
		}
	}

	public void menu() {
		if (gameState.getStatus()==Status.MENU 
				|| gameState.getStatus()==Status.HIGHSCORE 
				|| gameState.getStatus()==Status.OPTIONS
				|| gameState.getStatus()==Status.HOWTOPLAY
				|| gameState.getStatus()==Status.CHANGEPASS) {
			switch (gameState.getStatus()) {
			case MENU:
				//Change to new menusystem
				startMenu.update();
				if (gameState.getStatus()==Status.STARTGAME) {
					gameState.setStatus(Status.COUNTDOWN);
					count.setCountDownState(3);
				}
				break;
			case HIGHSCORE:
				//Change to new menusystem
				scoreMenu.update();
				break;
			case OPTIONS:
				options.runMenu();
				break;
			case HOWTOPLAY:
				howToPlay.update();
				rush.update();
				magnet.update();
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					gameState.setStatus(Status.MENU);
					magnet.setTimeLeft(0);
				}
				break;
			case CHANGEPASS:
				changePassword.runMenu();
				break;
			default:
				break;
			}
			if (gameState.getStatus().equals(Status.EXIT) || Display.isCloseRequested()){
				serverConnection.closeAllConnections();
				Display.destroy();
				return;
			}
		}
	}

	public void startScreen() {
		if (!Display.isCloseRequested() 
				&& (gameState.getStatus()==Status.LOGON 
				|| gameState.getStatus()==Status.CREATENEW 
				|| gameState.getStatus()==Status.LOSTPASSWORD
				|| gameState.getStatus()==Status.STARTSCREEN) 
				&& !(gameState.getStatus()==Status.EXIT)){
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				Display.destroy();
				serverConnection.closeAllConnections();
				return;
			}
			if (gameState.getStatus()==Status.LOGON) logonMenu.update();
			else if(gameState.getStatus()==Status.CREATENEW) createNew.update();
			else if(gameState.getStatus()==Status.LOSTPASSWORD) lostPassword.update();
			else {
				startScreen.runMenu();
			}
		}
	}


	private void updateTopLists() {
		serverConnection.updateTopLists();
	}

	private boolean isEscPressed(){
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			gameState.setStatus(Status.MENU);
			resetGame();
			return true;
		}
		return false;
	}

	private void resetGame() {
		this.gameObjHandler.getGameObjectArray().clear();
		this.score.setScoreMulti(1);
		this.score.resetCollisions();
		this.magnet.setTimeLeft(0);
		while (gameObjHandler.getStarsArray().size()>gameObjHandler.getNumberOfStars()) {
			gameObjHandler.getStarsArray().remove(gameObjHandler.getStarsArray().size()-1);
		}
		updateTopLists();
	}


	public void moveStars(){
		for (Star star : gameObjHandler.getStarsArray()) {
			star.update();
		}
	}

	public Score getScore() {
		return score;
	}
	public GameObjectHandler getGameObjHandler() {
		return gameObjHandler;
	}

	public LogonMenu getLogonMenu() {
		return logonMenu;
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

	public HighScoreMenu getScoreMenu() {
		return scoreMenu;
	}

	public void setScoreMenu(HighScoreMenu scoreMenu) {
		this.scoreMenu = scoreMenu;
	}

	public DisplayConfig getdConfig() {
		return dConfig;
	}

	public void setdConfig(DisplayConfig dConfig) {
		this.dConfig = dConfig;
	}

	public ServerConnection getServerConnection() {
		return serverConnection;
	}

	public Options getOptions() {
		return options;
	}

	public ChangePassword getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(ChangePassword changePassword) {
		this.changePassword = changePassword;
	}

	public LostPassword getLostPassword() {
		return lostPassword;
	}

	public CreateNewAccountMenu getCreateNew() {
		return createNew;
	}

	public void setCreateNew(CreateNewAccountMenu createNew) {
		this.createNew = createNew;
	}

	public StartScreen getStartScreen() {
		return startScreen;
	}

	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}
}
