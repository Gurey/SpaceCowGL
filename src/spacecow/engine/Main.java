package spacecow.engine;

public class Main {
	static Game g;	
	public static void main(String[] args) {
		new LoadNative();
		g = new Game();
		g.start();
	}

}
