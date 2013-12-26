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
	
	@Override
	public void run() {
		this.available=false;
		while (superSpeed<4) {
			superSpeed += increese;
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
