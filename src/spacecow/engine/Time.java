package spacecow.engine;

import org.lwjgl.Sys;

public class Time {

	private long timeLeft;
	
	//Dividing the time left with the resolution of the time to return seconds.
	public int getSecondsLeft(){
		int secondsLeft = (int) ((timeLeft-Sys.getTime())/Sys.getTimerResolution());
		return secondsLeft;
	}
	//increse the time left by seconds.
	public void incTimeLeft(int seconds){
		timeLeft += seconds*Sys.getTimerResolution();
	}
	//set time left.
	public void setTimeLeft(int seconds){
		timeLeft = Sys.getTime()+(seconds*Sys.getTimerResolution());
	}
	
}
