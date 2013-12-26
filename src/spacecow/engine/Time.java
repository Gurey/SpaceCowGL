package spacecow.engine;

import org.lwjgl.Sys;

public class Time {

	private static long timeLeft=Sys.getTime()+20000;
	
	public int getSecondsLeft(){
		int secondsLeft = (int) ((timeLeft-Sys.getTime())/Sys.getTimerResolution());
		return secondsLeft;
	}
	public void incTimeLeft(int seconds){
		timeLeft += seconds*Sys.getTimerResolution();
	}
	
}
