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

	private ArrayList<Star> starArrayList;
	private TextureHandler textureHandler;
	private GameState state;
	private DrawText drawInfo;
	private DrawText drawInput;
	private String mail1, mail2, passSecret;
	private float textPosX, mailPosY1, mailPosY2, backPosY;
	private KeyboadTextInput input;
	private Pointer pointer;
	private boolean stateChanged;
	private ServerConnection connection;

	public LostPassword(ArrayList<Star> starArrayList, TextureHandler textureHandler, GameState state, ServerConnection connection) {
		this.connection = connection;
		this.setStarArrayList(starArrayList);
		this.setTextureHandler(textureHandler);
		this.setState(state);
		this.drawInfo =  new DrawText(50, Alignment.RIGHT);
		this.drawInput = new DrawText(50, Alignment.LEFT);
		this.textPosX = Game.dWidth/2;
		this.mailPosY1 = (Game.dHeight/2)-50;
		this.mailPosY2 = (Game.dHeight/2)+50;
		this.backPosY = (Game.dHeight/2)+150;
		this.mail2 = "";
		this.mail1 = "";
		this.input = new KeyboadTextInput();
		this.pointer = new Pointer(350,mailPosY1, 100, 3, 1, textureHandler);
	}
	
	public void update(){
		for (Star star : starArrayList) {
			star.move();
		}
		pointer.updatePointerState();
		drawInfo.drawString(textPosX, mailPosY1, "eMail: ", Color.white);
		drawInfo.drawString(textPosX, mailPosY2, "eMail again: ", Color.white);
		drawInfo.drawString(textPosX, backPosY, "<- Back", Color.white);
		getInput();
		drawInput.drawString(textPosX, mailPosY1, mail1, Color.white);
		drawInput.drawString(textPosX, mailPosY2, mail2, Color.white);
		checkIfExe();
	}
	
	public void getInput(){
		switch (pointer.getPointerState()) {
		case 1:
			mail1 = input.getInput(drawInput, mail1,30);
			mail1 = mail1.trim();
			break;
		case 2:
			mail2 = input.getInput(drawInput, mail2,30);
			mail2 = mail2.trim();
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
	
	public ArrayList<Star> getStarArrayList() {
		return starArrayList;
	}

	public void setStarArrayList(ArrayList<Star> starArrayList) {
		this.starArrayList = starArrayList;
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
}
