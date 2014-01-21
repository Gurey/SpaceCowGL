package spacecow.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DisplayConfig {

	//This is the Class that lets us switch between full screen
	//and window mode.
	public void setDisplayMode(int width, int height, boolean fullscreen){
		
		//If the display mode we are trying to achieve is already running
		//we just jump straight back out.
		if((Display.getDisplayMode().getWidth() == width) && 
				(Display.getDisplayMode().getHeight() == height) &&
				(Display.isFullscreen() == fullscreen)){
			return;
		}
		try{
			DisplayMode targetDisplayMode = null;
			
			//if we are in full screen mode we will have to check and iterate
			//through the computers available display modes to get back to
			//where we started
			if(fullscreen){
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq =0;
				for (DisplayMode displayMode : modes) {
					System.out.println(displayMode.getWidth()+" "+displayMode.getHeight());
				}
				
				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];
					if((current.getWidth() == width) && (current.getHeight() == height)){
						if((targetDisplayMode == null) || (current.getFrequency() >= freq)){
							if((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())){
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}
						if((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
								(current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())){
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}
			if (targetDisplayMode == null){
				System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
				return;
			}
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
				
		} catch (LWJGLException e){
			System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
		}
	}
	
}
