package spacecow.buffs;


public class Rush implements Runnable{

	boolean available;
	private static Rush instance;
	private float vel;
	int cooldown=1000;
	
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
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		System.out.println(startTime);
		this.available=false;
		this.vel=15;
		int loopcount=0;
		while (this.vel>1.5) {
			loopcount++;
			this.vel=(float) (this.vel*0.5);
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO: handle exception
		}
		}
		System.out.println(loopcount);
		this.vel=1;
		long sleep = startTime+cooldown-System.currentTimeMillis();
		try {
			System.out.println("Rush sleeping for: "+sleep);
			Thread.sleep(sleep);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(System.currentTimeMillis()-startTime);
		this.available=true;
	}

	public boolean isAvailable() {
		return available;
	}

	public double getVel() {
		return vel;
	}

}
