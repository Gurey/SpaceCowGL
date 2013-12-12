package spacecow.engine;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class DrawPoints {

	private TrueTypeFont font;
	private float x, y;
	private String textToWrite;
	private long time;
	private static ArrayList<DrawPoints> drawPointsArray = new ArrayList<>();
	private static ArrayList<DrawPoints> removePointsArray = new ArrayList<>();
	
	
	public DrawPoints(TrueTypeFont fontIn, float playerX, float PlayerY, String textToWrite) {
		this.font = fontIn;
		this.x=playerX;
		this.y=PlayerY;
		this.textToWrite=textToWrite;
		this.time = Sys.getTime();
		drawPointsArray.add(this);
	}
	
	public void update(){
		if (Sys.getTime()<this.time+1000) {
			this.font.drawString(this.x, this.y, this.textToWrite, Color.green);
			Color.white.bind();
		}
		else {
			removePointsArray.add(this);
		}
		
	}

	public TrueTypeFont getFont() {
		return font;
	}

	public long getTime() {
		return time;
	}

	public static ArrayList<DrawPoints> getDrawPointsArray() {
		return drawPointsArray;
	}

	public static ArrayList<DrawPoints> getRemovePointsArray() {
		return removePointsArray;
	}
	
}
