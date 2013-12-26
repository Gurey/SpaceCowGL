package spacecow.engine;

import org.lwjgl.Sys;

public class IterationTimer {

	private int ticks;
	private long time=Sys.getTime(), total, start;
	int avg;
	
	public int getAverageIterationTime(){
		if (Sys.getTime()>time+1000){
			time=Sys.getTime();
			avg = (int) total/ticks;
			ticks=0;
			total = 0;
			System.out.println("Iteration time: "+avg);
			return avg;
		}
		ticks++;
		total += Sys.getTime()-start;
		return avg;
	}
	
	public void startTimer(){
		start = Sys.getTime();
	}
	public void refreshTime(){
		time = Sys.getTime();
	}
}
