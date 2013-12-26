package spacecow.engine;

import org.lwjgl.Sys;


public class FPS {

	private String FPS="0";
	private int tick;
	private long time;
	private boolean getFPS=true;
	
	public String getFPS(){
		if (Sys.getTime()>time+1000 || getFPS == true){
			time=Sys.getTime();
			getFPS = false;
			FPS = "FPS: "+tick;
			tick=0;
			return FPS;
		}
		tick++;
		return FPS;
	}
	
}
