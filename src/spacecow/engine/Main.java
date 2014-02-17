package spacecow.engine;

public class Main {
	static Game g;	
	public static void main(String[] args) {
		
//		Load natives to be able to run the rest of the program.
//		LoadNative nativeLoader = new LoadNative();
//		nativeLoader.loadNatives();
		new Game().start();
	}
}
