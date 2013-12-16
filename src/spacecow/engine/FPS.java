package spacecow.engine;


public class FPS {

	private static String FPS="0";
	private static int tick;
	private static long time;
	private static boolean getFPS=true;
	
	public static String getFPS(){
		if (System.currentTimeMillis()>time+1000 || getFPS == true){
			time=System.currentTimeMillis();
			getFPS = false;
			FPS = "FPS: "+tick;
			tick=0;
			return FPS;
		}
		tick++;
		return FPS;
	}
	
}
