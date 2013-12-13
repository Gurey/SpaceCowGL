package spacecow.engine;


import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class DrawPoints {

	private TrueTypeFont font;
	private float x, y;
	private String textToWrite;
	private long time;
	
	
	public DrawPoints(TrueTypeFont fontIn, float playerX, float PlayerY, String textToWrite) {
		this.font = fontIn;
		this.x=playerX;
		this.y=PlayerY;
		this.textToWrite=textToWrite;
		this.time = Sys.getTime();
		DrawText.getDrawPointsArray().add(this);
	}
	
	public void update(){
		if (Sys.getTime()<this.time+1000) {
			this.font.drawString(this.x, this.y, this.textToWrite, Color.green);
			Color.white.bind();
		}
		else {
			DrawText.getRemovePointsArray().add(this);
		}
		
	}

	public TrueTypeFont getFont() {
		return font;
	}

	public long getTime() {
		return time;
	}
}
