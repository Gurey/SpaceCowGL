package spacecow.buffs;

import java.util.ArrayList;

import org.lwjgl.Sys;

import spacecow.engine.GameObject;
import spacecow.objects.Player;

public class Magnet {

	private boolean idle=true;
	private long timeLeft;
	private float vel;
	private float starVel;
	private float xDiff=0, yDiff=0; 
	
	public ArrayList<GameObject> gameObjArr;
	public Player player;
	//Takes in a Player and an Array of GameObjects 
	//because we need to know where both the GameObject and the player are relative to each other
	public Magnet(ArrayList<GameObject> gameObjArr, Player player){
		this.gameObjArr = gameObjArr;
		this.player = player;
	}
	//init, setting the the idle to false so update() will enter the for each loop.
	//Sets the timeLeft to 5 seconds.
	public void initMagnet(){
	idle = false;
	timeLeft = Sys.getTime()+5000;
	for (GameObject go : gameObjArr) {
		if (go.isMagnetic()) {
			go.setSpeed(0);
		}
	}
	starVel = 0;
	}
	//Updated the state of the magnet moving all objects affected by it towards the player based on the difference of the distance.
	public void update(){
		if (!idle) {
		float texWC = player.getX()+(player.getTexture().getTextureWidth()/2);
		float texHC = player.getY()+(player.getTexture().getTextureHeight()/2);
		//Increase the velocity of the magnet if max velocity is not reached.  
		if (this.vel<18 && Sys.getTime()<timeLeft) {
			vel+=0.07f;
		}
		//Checking each corner based from the player witch direction the object should move.
		for (GameObject sb : gameObjArr){
			if (sb.isMagnetic()) {
			//upper right block
				sb.setSpeed(starVel);
				
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
		
		}
		// if time is up, this code sets everything to normal again.
		if (Sys.getTime()>timeLeft && vel>0) {
			vel -= 0.3;
			if (vel<16) {
				starVel += 0.14529;
			}
			if (vel<0) {
				idle = true;
				System.out.println(starVel);
			}
		}
		}

	}

	public boolean isAvailable() {
		return idle;
	}

	public long getTime() {
		return timeLeft;
	}
	//increase the time left in milliseconds
	public void incTime(int milliseconds) {
		this.timeLeft+=milliseconds;
	}
	//Return seconds left, used for printing out on screen.
	public long getTimeLeft(){
		return (timeLeft-Sys.getTime());
	}
	
}
