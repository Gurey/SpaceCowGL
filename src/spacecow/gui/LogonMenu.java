package spacecow.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.google.gson.Gson;

import spacecow.engine.DrawText;
import spacecow.engine.DrawText.Alignment;
import spacecow.engine.Game;
import spacecow.engine.GameState;
import spacecow.engine.GameState.Status;
import spacecow.engine.KeyboadTextInput;
import spacecow.engine.Pointer;
import spacecow.engine.TextureHandler;
import spacecow.objects.Star;
import spacecow.serverconnection.Json;
import spacecow.serverconnection.ServerConnection;

public class LogonMenu {

	private TextureHandler textureHandler;
	private GameState state;
	private DrawText drawInfo;
	private DrawText drawMessage;
	private DrawText drawInput;
	private String accountName, password, passSecret, message;
	private float textPosX, accPosY, passPosY, createPosY, lostPosY;
	private KeyboadTextInput input;
	private Pointer pointer;
	private boolean stateChanged;
	private ServerConnection connection;
	
	public LogonMenu(TextureHandler textureHandler, GameState state, ServerConnection connection){
		this.connection = connection;
		this.setTextureHandler(textureHandler);
		this.setState(state);
		this.message = "Welcome!";
		this.drawInfo =  new DrawText(50, Alignment.RIGHT);
		this.drawInput = new DrawText(50, Alignment.LEFT);
		this.drawMessage = new DrawText(35, Alignment.CENTER);
		this.textPosX = Game.dWidth/2;
		this.accPosY = (Game.dHeight/2)-150;
		this.passPosY = (Game.dHeight/2)-50;
		this.createPosY = (Game.dHeight/2)+50;
		this.lostPosY = (Game.dHeight/2)+150;
		this.password = "";
		this.accountName = "";
		this.input = new KeyboadTextInput();
		this.pointer = new Pointer(300,accPosY, 100, 4, 1, textureHandler);
	}
	
	public void update(){
		pointer.updatePointerState();
		drawInfo.drawString(textPosX, accPosY, "Name: ", Color.white);
		drawInfo.drawString(textPosX, passPosY, "Password: ", Color.white);
		drawInfo.drawString(textPosX, createPosY, "<- Back ", Color.white);
		drawInfo.drawString(textPosX, lostPosY, "Lost Password  ", Color.white);
		getInput();
		drawInput.drawString(textPosX, accPosY, accountName, Color.white);
		passSecret = "";
		for (int i = 0; i < password.length(); i++) {
			passSecret += "*";
		}
		drawInput.drawString(textPosX, passPosY, passSecret, Color.white);
		checkIfExe();
		drawMessage.drawString(Game.dWidth/2, Game.dHeight-100, message, Color.white);
	}
	
	public void getInput(){
		switch (pointer.getPointerState()) {
		case 1:
			accountName = input.getInput(drawInput, accountName,12);
			accountName = accountName.trim();
			accountName = accountName.replaceAll("\\W", "");
			break;
		case 2:
			password = input.getInput(drawInput, password,15);
			password = password.trim();
		default:
			break;
		}
	}
	
	public void checkIfExe(){
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			stateChanged = true;
		}
		else if (stateChanged && !Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			switch (pointer.getPointerState()) {
			case 1:
			case 2:
				Json jSon = new Json();
				jSon.setType("LOGIN");
				jSon.setName(accountName);
				jSon.setPassword(password);
				connection.send(new Gson().toJson(jSon, Json.class));
				stateChanged = false;
				break;
			case 3:
				state.setStatus(Status.STARTSCREEN);
				stateChanged = false;
				break;
			case 4:
				state.setStatus(Status.LOSTPASSWORD);
				stateChanged = false;
			default:
				break;
			}
		}
	}

	public TextureHandler getTextureHandler() {
		return textureHandler;
	}

	public void setTextureHandler(TextureHandler textureHandler) {
		this.textureHandler = textureHandler;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public DrawText getDrawInput() {
		return drawInput;
	}

	public void setDrawInput(DrawText drawInput) {
		this.drawInput = drawInput;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassSecret() {
		return passSecret;
	}

	public void setPassSecret(String passSecret) {
		this.passSecret = passSecret;
	}
	public void setConnection(ServerConnection connection){
		this.connection = connection;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
