package spacecow.engine;



import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

import spacecow.buffs.Magnet;
import spacecow.objects.ScoreMultiplyer;
import spacecow.objects.Player;
import spacecow.objects.Star;
import spacecow.objects.StarBuff;
import static org.lwjgl.opengl.GL11.*;

public class Game {
	
	TextureHandler sqr; 
	public static int dWidth = 1400;
	public static int dHeight = 750;
	
	ScoreMultiplyer p;
	
	 
	public Game(){
		initGL();
		Star.createStars(2000);
		DrawText.initText();
	}
	public void initGL(){
		try {
			Display.setFullscreen(true);
			Display.setDisplayMode(new DisplayMode(dWidth, dHeight));
			Display.setVSyncEnabled(true);
			Display.sync(60);
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
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			render();
			update();
			Display.update();
		}
		Display.destroy();
	}
	
	public void update(){
		Star.updateStars();
		ScoreMultiplyer.update();
		StarBuff.update();
		Player.getInstance().update();
		Magnet.update();
		
		Score.update();
		DrawText.update();
	}
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}
