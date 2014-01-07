package spacecow.buffs;


public class SuperSpeed implements Runnable {

	double newSpeed;
	
	private boolean available;
	
	private double increese=0.25;
	private double superSpeed;

	public SuperSpeed(){
		this.available=true;
		this.superSpeed=1;
	}
	
	//increase the speed of the stars in the background.
	@Override
	public void run() {
		this.available=false;
		//increase the speed
		while (superSpeed<4) {
			superSpeed += increese;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//holds the speed for x milliseconds
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//decrease the speed down to 1 again.
			while (superSpeed>1) {
				superSpeed -= increese;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			superSpeed=1;
			this.available=true;
		}		
	

	public boolean isAvailable() {
		return available;
	}
	
	public double getSuperSpeed() {
		return superSpeed;
	}
}
