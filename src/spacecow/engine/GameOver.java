package spacecow.engine;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;

import spacecow.objects.Star;

public class GameOver {
	
	ArrayList<Star> starsArray;
	DrawText dText;
	long time;
	private long finalScore;

	public GameOver(ArrayList<Star> starsArray){
		this.starsArray=starsArray;
		dText = new DrawText(55,true);
		time = Sys.getTime();
	}
	
	public void update(){
		for (Star star : starsArray) {
			star.move();
		}
		dText.drawString("gameover!", (float)Game.dWidth/2, (float)(Game.dHeight/2f)-25, Color.red);
		dText.drawString("Score: "+finalScore, (float)Game.dWidth/2, (float)(Game.dHeight/2)+25f, Color.white);
	}

	public long getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(long score){
		this.finalScore = score;
	}
	
}
