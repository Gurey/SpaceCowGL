package spacecow.buffs;

import org.lwjgl.Sys;


public class Rush implements Runnable{

	private boolean available;
	private static Rush instance;
	private float vel;
	private int cooldown=1500;
	
	private Rush(){
		this.available=true;
		this.vel=1;
	}
	
	public static Rush getInstance(){
		if (instance == null) {
			instance = new Rush();
		}
		return instance;
	}
	
	//sets the Rush velocity to 15 and the decrease with *0.5 every 100th of a second making the player move slower.
	@Override
	public void run() {
		long startTime = Sys.getTime();
		this.available=false;
		this.vel=15;
		while (this.vel>1.5) {
			this.vel=(float) (this.vel*0.5);
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO: handle exception
		}
		}
		this.vel=1;
		//continue the cooldown so that the Player can't use it again until the cooldown has passed.
		long sleep = startTime+cooldown-Sys.getTime();
		try {
			System.out.println("Rush sleeping for: "+sleep);
			Thread.sleep(sleep);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(Sys.getTime()-startTime);
		this.available=true;
	}

	public boolean isAvailable() {
		return available;
	}
	//vel is used in Player.move();
	public double getVel() {
		return vel;
	}

}
