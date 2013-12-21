package spacecow.engine;

import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;

public class CountDown {

	private static long time=Sys.getTime();
	private static int countdownState=3;
	private static Texture tex; 
	
	public static void countDown(){
		if (countdownState==3) {
			tex = TextureHandler.getInstance().getNumber3tex();
			if (Sys.getTime()>time+1000){
				countdownState=2;
				time=Sys.getTime();
			}
		}
		else if (countdownState==2) {
			tex = TextureHandler.getInstance().getNumber2tex();
			if (Sys.getTime()>time+1000){
				countdownState=1;
				time=Sys.getTime();
			}
		}
		else if (countdownState==1) {
			tex = TextureHandler.getInstance().getNumber1tex();
			if (Sys.getTime()>time+1000){
				countdownState=0;
			}
		}
		TextureHandler.getInstance().drawTexture(tex, (Game.dWidth/2)-250, (Game.dHeight/2)-250);
	}
	public static int getCountdownState(){
		return countdownState;
	}
	
}
