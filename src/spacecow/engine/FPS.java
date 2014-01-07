package spacecow.engine;

import org.lwjgl.Sys;


public class FPS {

	private String FPS="0";
	private int tick;
	private long time=Sys.getTime();
	
	//Calc the FPS, adding +1 to tick if one second has not passed sense last time reset.
	public String getFPS(){
		if (Sys.getTime()>time+1000){
			time=Sys.getTime();
			FPS = "FPS: "+tick;
			tick=0;
			return FPS;
		}
		tick++;
		return FPS;
	}
	
}
