package spacecow.buffs;

import org.lwjgl.Sys;


public class Rush{

	private boolean available;
	private float vel;
	private int cooldown=1500;
	private long startTime;

	public Rush(){
		this.available=true;
		this.vel=1;
	}

	public void initRush(){
		this.startTime = Sys.getTime();
		this.available=false;
		this.vel=10;
	}


	//sets the Rush velocity to 10 and the decrease with *0.5 every 100th of a second making the player move slower.
	public void update() {
		if (!available) {
			System.out.println(this.vel);
			this.vel=(float) (this.vel*0.80);
			System.out.println(this.vel);
			if (this.vel<1.2) {		
				System.out.println("setting vel to 1");
				this.vel=1;
			}
			//continue the cooldown so that the Player can't use it again until the cooldown has passed.
			if (startTime+cooldown<Sys.getTime()) {
				this.available=true;
			}
		}	
	}
	public boolean isAvailable() {
		return available;
	}
	//vel is used in Player.move();
	public double getVel() {
		return vel;
	}

	public long getStartTIme() {
		return startTime;
	}

	public void setStartTIme(long startTIme) {
		this.startTime = startTIme;
	}
	public void resetRush(){
		available = true;
		vel = 1;
	}

}
