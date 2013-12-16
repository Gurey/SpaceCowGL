package spacecow.engine;

import org.lwjgl.Sys;



public class Score {
	
	private static long score = 0;
	private static int scoreMulti=1;
	private static long scoreMultiTime;
	private static int latestScore;
	
	
	public static void incScoreBackground(int points){
		score+=(points*scoreMulti);
	}
	
	public static void incScore(int points){
		score+=(points*scoreMulti);
		setLatestScore(points*scoreMulti);
		DrawText.drawPoints();
	}
	public static long getScore(){
		return score;
	}
	public static void scoreMulti(){
		scoreMultiTime = Sys.getTime();
		scoreMulti++;
	}
	public static void update(){
		if (Sys.getTime()>scoreMultiTime+5000) {
			scoreMulti/=2;
			scoreMultiTime=Sys.getTime();
			if (scoreMulti<1) {
				scoreMulti=1;
			}
		}
	}
	public static int getScoreMulti(){
		return scoreMulti;
	}
	public static int getLatestScore() {
		return latestScore;
	}
	public static void setLatestScore(int latestScore) {
		Score.latestScore = latestScore;
	}
}
