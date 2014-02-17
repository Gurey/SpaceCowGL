package spacecow.gui;

import java.util.ArrayList;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import spacecow.engine.DrawText;
import spacecow.engine.DrawText.Alignment;
import spacecow.engine.Game;
import spacecow.engine.KeyboadTextInput;
import spacecow.engine.Pointer;
import spacecow.engine.TextureHandler;

public class Menu {

	private ArrayList<MenuObject> menuObjects;
	private String title, secretString, message;
	private DrawText drawMenu, drawTitle, drawInput, drawMessage;
	private KeyboadTextInput input;
	private Pointer pointer;
	private float startPosX, startPosY, padding;
	private boolean returnKeyRealesed;
	private Game game;
	
	public Menu(Game game) {
		this.setGame(game);
		this.startPosX = Game.dWidth/2;
		this.startPosY = Game.dHeight/3;
		this.returnKeyRealesed = false;
		this.padding = 100;
		this.menuObjects = new ArrayList<>();
		this.setTitle("");
		this.secretString = "";
		this.message = "";
		this.setDrawMenu(new DrawText(40, Alignment.RIGHT));
		this.drawInput = new DrawText(40, Alignment.LEFT);
		this.setDrawTitle(new DrawText(50, Alignment.CENTER));
		this.input = new KeyboadTextInput();
		this.pointer = new Pointer(startPosX, startPosY, padding, menuObjects.size(), 1, game.getTexHandler());
		this.drawMessage = new DrawText(35, Alignment.CENTER);
	}
	
	public void editString(){
			menuObjects.get(pointer.getPointerState()-1).setMenuInput(input.getInput(drawInput, menuObjects.get(pointer.getPointerState()-1).getMenuInput(), 100));
			if (menuObjects.get(pointer.getPointerState()-1).isSecret()){
				menuObjects.get(pointer.getPointerState()-1).setMenuInput(menuObjects.get(pointer.getPointerState()-1).getMenuInput().replaceAll("\\W", ""));
			}
	}
	
	public void update(){
		float yPos = startPosY;
		drawTitle.drawString(Game.dWidth/2, Game.dHeight/10, title, Color.white);
		for (MenuObject object : menuObjects) {
			drawMenu.drawString(startPosX, yPos, object.getMenuText(), Color.white);
			if (object.isHaveInputField()) {
				if (object.isSecret()) {
					secretString = "";
					for (int i = 0; i < object.getMenuInput().length(); i++) {
					secretString += "*";	
					}
					drawInput.drawString(startPosX, yPos, secretString, Color.white);
				}
				else drawInput.drawString(startPosX, yPos, object.getMenuInput(), Color.white);
			}
			yPos += padding;
		}
		drawMessage.drawString(Game.dWidth/2, (float) (Game.dHeight/1.2), message, Color.white);
		pointer.updatePointerState();
		editString();
		if (checkEnter()) {
			checkIfExe();
		}
	}
	
	public boolean checkEnter(){
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !returnKeyRealesed) returnKeyRealesed = true;
		else if (!Keyboard.isKeyDown(Keyboard.KEY_RETURN) && returnKeyRealesed){
			returnKeyRealesed = false;
			System.out.println("Execute!");
			return true;
		}
		return false;
	}
	
	public void checkIfExe() {
		//Pointerstate can be 1 to the maximum amount of pointerstates:
	}
	
	public void addMenuObject(String menuText, boolean haveInputString, boolean secretInput){
		menuObjects.add(new MenuObject(menuText, haveInputString, secretInput));
		pointer.setPointerStateMax(menuObjects.size());
		if (pointer.getStartPosX() > (startPosX - drawMenu.getTextWidht(menuText))) {
			pointer.setStartPosX(startPosX - drawMenu.getTextWidht(menuText) - 75);
		}
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DrawText getDrawMenu() {
		return drawMenu;
	}

	public void setDrawMenu(DrawText drawMenu) {
		this.drawMenu = drawMenu;
	}

	public DrawText getDrawTitle() {
		return drawTitle;
	}

	public void setDrawTitle(DrawText drawTitle) {
		this.drawTitle = drawTitle;
	}

	public ArrayList<MenuObject> getMenuObjects() {
		return menuObjects;
	}

	public DrawText getDrawInput() {
		return drawInput;
	}

	public KeyboadTextInput getInput() {
		return input;
	}

	public Pointer getPointer() {
		return pointer;
	}

	public float getStartPosX() {
		return startPosX;
	}

	public float getStartPosY() {
		return startPosY;
	}

	public float getPadding() {
		return padding;
	}

	public void setMenuObjects(ArrayList<MenuObject> menuObjects) {
		this.menuObjects = menuObjects;
	}

	public void setDrawInput(DrawText drawInput) {
		this.drawInput = drawInput;
	}

	public void setInput(KeyboadTextInput input) {
		this.input = input;
	}

	public void setPointer(Pointer pointer) {
		this.pointer = pointer;
	}

	public void setStartPosX(float startPosX) {
		this.startPosX = startPosX;
		this.pointer.setStartPosX(startPosX);
	}

	public void setStartPosY(float startPosY) {
		this.startPosY = startPosY;
		this.pointer.setStartPosY(startPosY);
	}

	public void setPadding(float padding) {
		this.padding = padding;
		this.pointer.setPadding(padding);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
