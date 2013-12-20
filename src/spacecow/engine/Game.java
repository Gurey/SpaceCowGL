package spacecow.engine;



import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import spacecow.buffs.Magnet;
import spacecow.objects.Cookie;
import spacecow.objects.GameObjectHandler;
import spacecow.objects.ScoreMultiplyer;
import spacecow.objects.Player;
import spacecow.objects.Star;
import spacecow.objects.StarBuff;
import static org.lwjgl.opengl.GL11.*;

public class Game {
	
	public static int dWidth = 1400;
	public static int dHeight = 750;

	
	Time time = new Time();
	
	public Game(){
		initGL();
		Star.createStars(1000);
		DrawText.initText();
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
//		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
//			Display.sync(60);
//			render();
//			if (CountDown.getCountdownState()<=0) {
//				break;
//			}
//			Player.getInstance().update();
//			Star.updateStars();
//			CountDown.countDown();
//			Display.update();
//		}
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !(time.getSecondsLeft()<=0)) {
			Display.sync(60);
			render();
			update();
			Display.update();
		}
		Display.destroy();
	}
	public void update(){
//		StarBuff.update();
		GameObjectHandler.getInstance().update();
		Player.getInstance().update();
		Magnet.update();
		Score.update();
		DrawText.update();
	}
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}
