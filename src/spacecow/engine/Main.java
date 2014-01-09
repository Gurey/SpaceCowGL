package spacecow.engine;

import java.awt.EventQueue;

import spacecow.gui.StartGUI;

public class Main {
	static Game g;	
	public static void main(String[] args) {
		//Load natives to be able to run the rest of the program.
		LoadNative nativeLoader = new LoadNative();
		nativeLoader.loadNatives();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGUI frame = new StartGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
