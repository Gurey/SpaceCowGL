package spacecow.engine;

import java.util.ArrayList;


import spacecow.objects.Star;

public class GameOver {
	
	ArrayList<Star> starsArray;
	private long finalScore;

	public GameOver(ArrayList<Star> starsArray){
		this.starsArray=starsArray;
	}
	//Moves the stars in the background.
	public void update(){
		for (Star star : starsArray) {
			star.move();
		}
	}

	public long getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(long score){
		this.finalScore = score;
	}
	
}
