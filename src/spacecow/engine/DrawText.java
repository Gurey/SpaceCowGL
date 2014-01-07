package spacecow.engine;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;


public class DrawText {

	private TrueTypeFont font;
	private boolean isCentered;
	
	public DrawText(int size, boolean isCentered){
		Font score = new Font("Arial", Font.BOLD, size);
		font = new TrueTypeFont(score, false);
		this.isCentered = isCentered;
	}
	//Printing text to the screen using the font.
	public void drawString(float x, float y, String text, Color color){
		if (this.isCentered) {
		float textWidth = (float)font.getWidth(text)/2; //calculating the width of the text dividing by 2.
		font.drawString(x - textWidth, y, text, color);
		}
		else{
		font.drawString(x, y, text, color);
		}
	}
}
