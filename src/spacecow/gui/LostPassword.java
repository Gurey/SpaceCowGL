package spacecow.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.google.gson.Gson;

import spacecow.engine.DrawText;
import spacecow.engine.Game;
import spacecow.engine.GameState;
import spacecow.engine.KeyboadTextInput;
import spacecow.engine.Pointer;
import spacecow.engine.TextureHandler;
import spacecow.engine.DrawText.Alignment;
import spacecow.engine.GameState.Status;
import spacecow.objects.Star;
import spacecow.serverconnection.Json;
import spacecow.serverconnection.ServerConnection;

public class LostPassword {

	private TextureHandler textureHandler;
	private GameState state;
	private DrawText drawInfo;
	private DrawText drawInput;
	private DrawText drawMessage;
	private String mail1, mail2, passSecret, message;
	private float textPosX, mailPosY1, mailPosY2, backPosY;
	private KeyboadTextInput input;
	private Pointer pointer;
	private boolean stateChanged;
	private ServerConnection connection;
	private Color matchColor;

	public LostPassword(TextureHandler textureHandler, GameState state, ServerConnection connection) {
		this.connection = connection;
		this.setTextureHandler(textureHandler);
		this.setState(state);
		this.drawInfo =  new DrawText(50, Alignment.RIGHT);
		this.drawInput = new DrawText(50, Alignment.LEFT);
		this.drawMessage = new DrawText(35, Alignment.CENTER);
		this.textPosX = Game.dWidth/2;
		this.mailPosY1 = (Game.dHeight/2)-50;
		this.mailPosY2 = (Game.dHeight/2)+50;
		this.backPosY = (Game.dHeight/2)+150;
		this.mail2 = "";
		this.mail1 = "";
		this.message = "";
		this.input = new KeyboadTextInput();
		this.pointer = new Pointer(350,mailPosY1, 100, 3, 1, textureHandler);
		this.matchColor = Color.white;
	}
	
	public void update(){
		
		if (mail1.equals(mail2) && mail1.length()>5) {
			matchColor = Color.green;
		}
		else matchColor = Color.white;
		
		pointer.updatePointerState();
		drawInfo.drawString(textPosX, mailPosY1, "eMail: ", matchColor);
		drawInfo.drawString(textPosX, mailPosY2, "eMail again: ", matchColor);
		drawInfo.drawString(textPosX, backPosY, "<- Back", Color.white);
		getInput();
		drawInput.drawString(textPosX, mailPosY1, mail1, Color.white);
		drawInput.drawString(textPosX, mailPosY2, mail2, Color.white);
		checkIfExe();
		drawMessage.drawString(Game.dWidth/2, Game.dHeight-100, message, Color.white);
	}
	
	public void getInput(){
		switch (pointer.getPointerState()) {
		case 1:
			mail1 = input.getInput(drawInput, mail1,50);
			mail1 = mail1.trim();
			break;
		case 2:
			mail2 = input.getInput(drawInput, mail2,50);
			mail2 = mail2.trim();
			break;
		default:
			break;
			
		}
	}
	
	public void checkIfExe(){
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			stateChanged = true;
		}
		else if (stateChanged && !Keyboard.isKeyDown(Keyboard.KEY_RETURN) && mail1.equals(mail2) && pointer.getPointerState()<3){
				Json jSon = new Json();
				jSon.setType("LOSTPASS");
				jSon.seteMail(mail1);
				connection.send(new Gson().toJson(jSon, Json.class));
				stateChanged = false;
		}
		else if (stateChanged && !Keyboard.isKeyDown(Keyboard.KEY_RETURN) && pointer.getPointerState()==3) {
			state.setStatus(Status.LOGON);
			stateChanged = false;
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
		return mail1;
	}

	public void setAccountName(String accountName) {
		this.mail1 = accountName;
	}

	public String getPassword() {
		return mail2;
	}

	public void setPassword(String password) {
		this.mail2 = password;
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
