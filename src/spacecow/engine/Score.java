package spacecow.engine;


import org.lwjgl.Sys;
import org.newdawn.slick.Color;



public class Score {
	
	private long score = 0;
	private int scoreMulti=1;
	private long scoreMultiTime;
	
	
	public void incScoreBackground(int points){
		score+=(points*scoreMulti);
	}
	public void incScore(int points){
		score+=(points*scoreMulti);
	}
	public long getScore(){
		return score;
	}
	public void setScore(long score){
		this.score = score;
	}
	public void scoreMulti(){
		scoreMultiTime = Sys.getTime();
		scoreMulti++;
	}
	public void update(){
		if (Sys.getTime()>scoreMultiTime+5000) {
			scoreMulti/=2;
			scoreMultiTime=Sys.getTime();
			if (scoreMulti<1) {
				scoreMulti=1;
			}
		}
	}
	public int getScoreMulti(){
		return scoreMulti;
	}
	public void setScoreMulti(int ScoreMul){
		scoreMulti = ScoreMul;
	}
}
