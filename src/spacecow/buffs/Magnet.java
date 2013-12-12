package spacecow.buffs;

import org.lwjgl.Sys;

import spacecow.objects.Player;
import spacecow.objects.StarBuff;

public class Magnet {

	private static boolean available=true;
	private static long time;
	private static float vel;
	private static float xDiff=0, yDiff=0; 
	
	public static void initMagnet(){
	available = false;
	time = Sys.getTime();
	}
	
	public static void update(){
		if (!available) {
				vel+=0.01f;
		for (StarBuff sb : StarBuff.getSbArray()) {
			sb.setSpeed(0);
			//upper right block
			if (sb.getX()>=Player.getInstance().getX() && sb.getY()<=Player.getInstance().getY()) {
				 xDiff = sb.getX() - Player.getInstance().getX();
				 yDiff = Player.getInstance().getY() - sb.getY();
				 if (xDiff>yDiff) {
					sb.setX(sb.getX()-(vel+(xDiff/200)));
					sb.setY(sb.getY()+(vel*2));
					System.out.println("xdif="+xDiff+"ydif="+yDiff);
					System.out.println("playerx "+Player.getInstance().getX()+" PlayerY "+Player.getInstance().getY());
				 }
				 else {
					sb.setX(sb.getX()-(vel*2));
					sb.setY(sb.getY()+(vel+(yDiff/200)));
					System.out.println("koer y");
				}
			}
			
//			else{
//				sb.setX(sb.getX()+vel);
//			}
//			if (sb.getY()>Player.getInstance().getY()) {
//				sb.setY(sb.getY()-vel);
//			}
//			else{
//				sb.setY(sb.getY()+vel);
//			}
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
