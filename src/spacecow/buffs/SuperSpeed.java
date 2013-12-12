package spacecow.buffs;

import spacecow.objects.Star;

public class SuperSpeed implements Runnable {

	double newSpeed;
	
	private boolean available;
	
	private static SuperSpeed instance;
	
	private double increese=0.25;
	
	private SuperSpeed(){
		this.available=true;
	}
	
	public static SuperSpeed getInstance(){
		if (instance == null) {
			instance = new SuperSpeed();
		}
		return instance;
	}
	
	@Override
	public void run() {
		this.available=false;
		while (Star.getSuperSpeed()<4) {
			newSpeed = Star.getSuperSpeed()+increese;
			Star.setSuperSpeed(newSpeed);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			while (Star.getSuperSpeed()>1) {
				newSpeed = Star.getSuperSpeed()-increese;
				Star.setSuperSpeed(newSpeed);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Star.setSuperSpeed(1);
			this.available=true;
		}		
	

	public boolean isAvailable() {
		return available;
	}

}
