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
		startMenu = new StartMenu(texHandler, gameState);
		count = new CountDown(gameObjHandler.getStarsArray(), texHandler);
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
		while (!Display.isCloseRequested() && !gameState.getStatus().equals(Status.EXIT)) {
			System.out.println("<Entering gameloop with GameState: "+gameState.getStatus()+">");
			while (!Display.isCloseRequested() 
					&& (gameState.getStatus()==Status.LOGON 
					|| gameState.getStatus()==Status.CREATENEW 
					|| gameState.getStatus()==Status.LOSTPASSWORD
					|| gameState.getStatus()==Status.STARTSCREEN) 
					&& !(gameState.getStatus()==Status.EXIT)){
				render();
				moveStars();
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					Display.destroy();
					serverConnection.closeAllConnections();
					return;
				}
				if (gameState.getStatus()==Status.LOGON) logonMenu.update();
				else if(gameState.getStatus()==Status.CREATENEW) createNew.update();
				else if(gameState.getStatus()==Status.LOSTPASSWORD) lostPassword.update();
				else {
					startScreen.update();;
				}
				Display.update();
				Display.sync(60);

			}
			updateTopLists();
			while (gameState.getStatus()==Status.MENU 
					|| gameState.getStatus()==Status.HIGHSCORE 
					|| gameState.getStatus()==Status.OPTIONS
					|| gameState.getStatus()==Status.HOWTOPLAY
					|| gameState.getStatus()==Status.CHANGEPASS) {
				render();
				if (gameState.getStatus() != Status.HOWTOPLAY) moveStars();
				switch (gameState.getStatus()) {
				case MENU:
					startMenu.update();
					break;
				case HIGHSCORE:
					scoreMenu.update();
					break;
				case OPTIONS:
					options.update();
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
					changePassword.update();
					break;
				default:
					break;
				}
				Display.update();
				Display.sync(60);
				if (gameState.getStatus().equals(Status.EXIT) || Display.isCloseRequested()){
					serverConnection.closeAllConnections();
					Display.destroy();
					return;
				}
			}
			//Starts the CoundDown of the game, exits when countdown is over.
			count.setCountDownState(3);
			while (!isEscPressed() && gameState.getStatus().equals(Status.STARTGAME)) {
				render();
				if (count.getCountdownState()<=0) {
					break;
				}
				player.update();
				rush.update();
				magnet.update();
				count.countDown();
				Display.update();
				Display.sync(60);
			}
			//Sets the Score to 0 and the time left to XX seconds.
			score.setScore(0);
			time.setTimeLeft(60);
			long startTime = Sys.getTime();
			rush.resetRush();
			gameObjHandler.resetObjectTimers();
			resetGame();
			//init the Game, running until the Player press Esc or the time runs out.
			while (!isEscPressed() && !(time.getSecondsLeft()<=0) && gameState.getStatus().equals(Status.STARTGAME)) {
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
			gOver.setDuration(Sys.getTime()-startTime);
			while (!isEscPressed() && time.getSecondsLeft()<=0 && gameState.getStatus().equals(Status.STARTGAME)) {
				render();
				gOver.update();
				textHandler.updateGameOver();
				Display.update();
				Display.sync(60);
			}
			resetGame();
			System.out.println("Game reset");
		}
		
		if (serverConnection != null) {
			serverConnection.closeAllConnections();
			System.out.println("Connections Closed!");
		}
		Display.destroy();
	}


	private void updateTopLists() {
		Json j = new Json();
		j.setType("TOPTEN");
		serverConnection.send(new Gson().toJson(j, Json.class));
		j.setType("PERSONALTOPTEN");
		serverConnection.send(new Gson().toJson(j, Json.class));
		j.setType("BESTAVG");
		serverConnection.send(new Gson().toJson(j, Json.class));
	}
	
	private boolean isEscPressed(){
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			gameState.setStatus(Status.MENU);
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
	
	public void moveStars(){
		for (Star star : gameObjHandler.getStarsArray()) {
			star.move();
		}
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
