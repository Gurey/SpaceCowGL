package spacecow.engine;

import org.lwjgl.Sys;

public class Score {
	
	private long score = 0;
	private int scoreMulti=1;
	private long scoreMultiTime;
	private boolean removeMulti = false;
	private int magnetCol, multiCol, cookiaCol, astroidCol, starCol;
	
	//Increase the score, multiplying it with the current score multiplyer.
	public void incScore(int points){
		score+=(points*scoreMulti);
	}
	//Adding 1 to the score multi, resets the time to the current System time
	public void scoreMulti(){
		scoreMulti++;
		scoreMultiTime = Sys.getTime();
		removeMulti = false;
	}
	//Checks if the scoreMulti should be decreased.
	//If the player has not captured a ScoreMultiplyer within x seconds, the scoreMulti will be decreased by 1 every x seconds.
	public void update(){
		if (Sys.getTime()>scoreMultiTime+5000 || (removeMulti && Sys.getTime()>scoreMultiTime+500) ) {
			removeMulti = true;
			scoreMulti--;
			scoreMultiTime=Sys.getTime();
			if (scoreMulti<1) {
				scoreMulti=1;
			}
		}
	}
	public void resetCollisions(){
		this.astroidCol = 0;
		this.cookiaCol = 0;
		this.magnetCol = 0;
		this.multiCol = 0;
		this.starCol = 0;
	}
	public int getScoreMulti(){
		return scoreMulti;
	}
	public void setScoreMulti(int ScoreMul){
		scoreMulti = ScoreMul;
	}
	public long getScore(){
		return score;
	}
	public void setScore(long score){
		this.score = score;
	}
	public int getMagnetCol() {
		return magnetCol;
	}
	public int getMultiCol() {
		return multiCol;
	}
	public int getCookiaCol() {
		return cookiaCol;
	}
	public int getAstroidCol() {
		return astroidCol;
	}
	public int getStarCol() {
		return starCol;
	}
	public void setMagnetCol(int magnetCol) {
		this.magnetCol = magnetCol;
	}
	public void setMultiCol(int multiCol) {
		this.multiCol = multiCol;
	}
	public void setCookiaCol(int cookiaCol) {
		this.cookiaCol = cookiaCol;
	}
	public void setAstroidCol(int astroidCol) {
		this.astroidCol = astroidCol;
	}
	public void setStarCol(int starCol) {
		this.starCol = starCol;
	}
}