package spacecow.engine;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;


public class DrawText {

	private TrueTypeFont font;
	public enum Alignment{LEFT,CENTER,RIGHT};
	private Alignment alignment;
	Font score;
	
	public DrawText(int size, Alignment alignment){
		score = new Font("Arial", Font.BOLD, size);
		font = new TrueTypeFont(score, false);
		this.alignment = alignment;
	}
	//Printing text to the screen using the font.
	public void drawString(float x, float y, String text, Color color){
		float textWidth=0;
		if (this.alignment.equals(Alignment.CENTER)) {
		textWidth = (float)font.getWidth(text)/2; //calculating the width of the text dividing by 2.
		}
		else if (this.alignment.equals(Alignment.RIGHT)) {
		textWidth = (float)font.getWidth(text);
		}
		font.drawString(x-textWidth, y, text, color);
	}
	public boolean canPrint(char c){
		return score.canDisplay(c);
	}
	public float getTextWidht(String s){
		float width;
		width = (float) font.getWidth(s);
		return width;
	}
}
