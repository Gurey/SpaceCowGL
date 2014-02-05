package spacecow.engine;

import org.lwjgl.input.Keyboard;

public class KeyboadTextInput {
	
	private boolean keyReleased;
	private String stringToSendBack;
	
	public String getInput(DrawText drawText, String stringToEdit, int stringMaxSize){
		char keyPressed = Keyboard.CHAR_NONE;
		if (keyReleased && Keyboard.getEventKeyState() && !Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			keyPressed = Keyboard.getEventCharacter();
			keyReleased = false;
			if (drawText.canPrint(keyPressed) && !(stringToEdit.length()>=stringMaxSize)) {
				stringToEdit += keyPressed; 
				System.out.print(keyPressed);
			}
		}
		else if (keyReleased && Keyboard.getEventKeyState() && Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			keyReleased = false;
			if (!stringToEdit.isEmpty()) {				
				stringToEdit = stringToEdit.substring(0, stringToEdit.length()-1);
			}
		}
		else if (Keyboard.next()) {
			keyReleased = true;
		}
		stringToSendBack = stringToEdit;
		return stringToSendBack;
	}
}
