package spacecow.engine;

import java.io.File;


public class LoadNative {

	public LoadNative(){
		 System.setProperty("java.library.path", "libs");
		 String os = System.getProperty("os.name").toLowerCase();
		 System.out.println(os);
		 if (os.equals("mac")) {
			 System.setProperty("org.lwjgl.librarypath", new File("res/LWJGLNative/macosx").getAbsolutePath());
		}
	}
	
}
