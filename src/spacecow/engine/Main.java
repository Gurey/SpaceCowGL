package spacecow.engine;

public class Main {
	static Game g;	
	public static void main(String[] args) {
		LoadNative nativeLoader = new LoadNative();
		nativeLoader.loadNatives();
		g = new Game();
		g.start();
	}

}
