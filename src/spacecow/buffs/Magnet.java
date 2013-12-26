package spacecow.buffs;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import spacecow.engine.GameObject;
import spacecow.objects.Player;

public class Magnet {

	private boolean available=true;
	private long time;
	private float vel;
	private float xDiff=0, yDiff=0; 
	
	public ArrayList<GameObject> gameObjArr;
	public Player player;
	
	public Magnet(ArrayList<GameObject> gameObjArr, Player player){
		this.gameObjArr = gameObjArr;
		this.player = player;
	}
	
	public void initMagnet(){
	available = false;
	time = Sys.getTime();
	}
	
	public void update(){
		if (Keyboard.isKeyDown(Keyboard.KEY_2) && (isAvailable())) {
		initMagnet();
		}
		if (!available) {
		float texWC = player.getX()+(player.getTexture().getTextureWidth()/2);
		float texHC = player.getY()+(player.getTexture().getTextureHeight()/2);
		vel+=0.07f;
		for (GameObject sb : gameObjArr){
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
			for (GameObject sb : gameObjArr) {
				sb.setSpeed(15);
			}
		}
		}
		
	}

	public boolean isAvailable() {
		return available;
	}
	
}
