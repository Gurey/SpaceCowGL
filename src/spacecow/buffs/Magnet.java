package spacecow.buffs;

import org.lwjgl.Sys;

import spacecow.engine.GameObject;
import spacecow.objects.GameObjectHandler;
import spacecow.objects.Player;

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
		float texWC = Player.getInstance().getX()+(Player.getInstance().getTexture().getTextureWidth()/2);
		float texHC = Player.getInstance().getY()+(Player.getInstance().getTexture().getTextureHeight()/2);
		vel+=0.07f;
		for (GameObject sb : GameObjectHandler.getInstance().getGameObjectArray()){
			sb.setSpeed(0);
			//upper right block
			if (sb.getX()>=texWC && sb.getY()<=texHC) {
				 xDiff = sb.getX() - texWC;
				 yDiff = texHC - sb.getY();
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
			else if (sb.getX()>=texWC && sb.getY()>=texHC) {
				xDiff = sb.getX() - texWC;
				yDiff = sb.getY() - texHC;
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
			else if (sb.getX()<=texWC && sb.getY()>=texHC) {
				 xDiff = texWC - sb.getX();
				 yDiff = sb.getY() - texHC;
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
				 xDiff = texWC - sb.getX();
				 yDiff = texHC - sb.getY();
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
			for (GameObject sb : GameObjectHandler.getInstance().getGameObjectArray()) {
				sb.setSpeed(15);
			}
		}
		}
		
	}

	public static boolean isAvailable() {
		return available;
	}
	
}
