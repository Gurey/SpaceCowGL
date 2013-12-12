package spacecow.engine;

public class Main {
	static Game g;	
	public static void main(String[] args) {
		System.out.println(Thread.activeCount());
		g = new Game();
		g.start();

	}

}
