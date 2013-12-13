package spacecow.buffs;

import org.lwjgl.Sys;

import spacecow.objects.ObjectArrays;
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
		vel+=0.09f;
		for (StarBuff sb : ObjectArrays.getSbArray()) {
//			sb.setSpeed(0);
			//upper right block
			if (sb.getX()>=Player.getInstance().getX() && sb.getY()<=Player.getInstance().getY()) {
				 xDiff = sb.getX() - Player.getInstance().getX();
				 yDiff = Player.getInstance().getY() - sb.getY();
				 if (xDiff>yDiff) {
					sb.setX(sb.getX()-(vel+(xDiff/200)));
					sb.setY(sb.getY()+(yDiff*(vel*0.0035f)));
				 }
				 else {
					sb.setX(sb.getX()-(xDiff*(vel*0.0035f)));
					sb.setY(sb.getY()+(vel+(yDiff/200)));
				}
			}
			//lower right
			else if (sb.getX()>=Player.getInstance().getX() && sb.getY()>=Player.getInstance().getY()) {
				 xDiff = sb.getX() - Player.getInstance().getX();
				 yDiff = sb.getY() - Player.getInstance().getY();
				 if (xDiff>yDiff) {
					sb.setX(sb.getX()-(vel+(xDiff/200)));
					sb.setY(sb.getY()-(yDiff*(vel*0.0035f)));
				 }
				 else {
					sb.setX(sb.getX()-(xDiff*(vel*0.0035f)));
					sb.setY(sb.getY()-(vel+(yDiff/200)));
				}
			}
			//lower left
			else if (sb.getX()<=Player.getInstance().getX() && sb.getY()>=Player.getInstance().getY()) {
				 xDiff = Player.getInstance().getX() - sb.getX();
				 yDiff = sb.getY() - Player.getInstance().getY();
				 if (xDiff>yDiff) {
					sb.setX(sb.getX()+(vel+(xDiff/200)));
					sb.setY(sb.getY()-(yDiff*(vel*0.0035f)));
				 }
				 else {
					sb.setX(sb.getX()+(xDiff*(vel*0.0035f)));
					sb.setY(sb.getY()-(vel+(yDiff/200)));
				}
			}
			//Upper left
			else {
				 xDiff = Player.getInstance().getX() - sb.getX();
				 yDiff = Player.getInstance().getY() - sb.getY();
				 if (xDiff>yDiff) {
					sb.setX(sb.getX()+(vel+(xDiff/200)));
					sb.setY(sb.getY()+(yDiff*(vel*0.0035f)));
				 }
				 else {
					sb.setX(sb.getX()+(xDiff*(vel*0.0035f)));
					sb.setY(sb.getY()+(vel+(yDiff/200)));
				}
			}
		}
		if (Sys.getTime()>time+5000) {
			available = true;
			vel = 0;
			for (StarBuff sb : ObjectArrays.getSbArray()) {
				sb.setSpeed(10);
			}
		}
		}
		
	}

	public static boolean isAvailable() {
		return available;
	}
	
}
