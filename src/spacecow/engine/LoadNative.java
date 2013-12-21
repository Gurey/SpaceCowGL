package spacecow.engine;

import java.io.File;

public class LoadNative {
	
	private String os = System.getProperty("os.name").toLowerCase();
	
	public void loadNatives(){
		 System.out.println(os);
		 if (isWindows()) {
				System.out.println("This is Windows");
				System.setProperty("org.lwjgl.librarypath", new File("LWJGL/LWJGLNative/windows").getAbsolutePath());
			} else if (isMac()) {
				System.out.println("This is Mac");
				System.setProperty("org.lwjgl.librarypath", new File("LWJGL/LWJGLNative/macosx").getAbsolutePath());
			} else {
				System.out.println("Your OS is not support!!");
			}
	}
	public boolean isWindows() {
		 
		return (os.indexOf("win") >= 0);
 
	}
 
	public boolean isMac() {
 
		return (os.indexOf("mac") >= 0);
 
	}
	
}
