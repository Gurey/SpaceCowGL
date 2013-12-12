package spacecow.buffs;

import org.lwjgl.Sys;

import spacecow.objects.Player;
import spacecow.objects.StarBuff;

public class Magnet {

	private static boolean available=true;
	private static long time;
	private static float vel;
	
	public static void initMagnet(){
	available = false;
	time = Sys.getTime();
	}
	
	public static void update(){
		if (!available) {
		vel+=0.09f;
		for (StarBuff sb : StarBuff.getSbArray()) {
			sb.setSpeed(0);
			if (sb.getX()>=Player.getInstance().getX()) {
				sb.setX(sb.getX()-vel);
			}
			else{
				sb.setX(sb.getX()+vel);
			}
			if (sb.getY()>=Player.getInstance().getY()) {
				sb.setY(sb.getY()-vel);
			}
			else{
				sb.setY(sb.getY()+vel);
			}
		}
		if (Sys.getTime()>time+5000) {
			available = true;
			vel = 0;
			for (StarBuff sb : StarBuff.getSbArray()) {
				sb.setSpeed(10);
			}
		}
		}
		
	}

	public static boolean isAvailable() {
		return available;
	}
	
}
