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

	private ArrayList<Star> starArrayList;
	private TextureHandler textureHandler;
	private GameState state;
	private DrawText drawInfo;
	private DrawText drawInput;
	private String accountName, password, passSecret;
	private float textPosX, accPosY, passPosY, createPosY;
	private KeyboadTextInput input;
	private Pointer pointer;
	private boolean stateChanged;
	private ServerConnection connection;
	
	public LogonMenu(ArrayList<Star> starArrayList, TextureHandler textureHandler, GameState state, ServerConnection connection){
		this.connection = connection;
		this.setStarArrayList(starArrayList);
		this.setTextureHandler(textureHandler);
		this.setState(state);
		this.drawInfo =  new DrawText(50, Alignment.RIGHT);
		this.drawInput = new DrawText(50, Alignment.LEFT);
		this.textPosX = Game.dWidth/2;
		this.accPosY = (Game.dHeight/2)-150;
		this.passPosY = (Game.dHeight/2)-50;
		this.createPosY = (Game.dHeight/2)+50;
		this.password = "";
		this.accountName = "";
		this.input = new KeyboadTextInput();
		this.pointer = new Pointer(350,accPosY, 100, 4, 1, textureHandler);
	}
	
	public void update(){
		for (Star star : starArrayList) {
			star.move();
		}
		pointer.updatePointerState();
		drawInfo.drawString(textPosX, accPosY, "Name: ", Color.white);
		drawInfo.drawString(textPosX, passPosY, "Password: ", Color.white);
		drawInfo.drawString(textPosX, createPosY, "Create new  ", Color.white);
		getInput();
		drawInput.drawString(textPosX, accPosY, accountName, Color.white);
		passSecret = "";
		for (int i = 0; i < password.length(); i++) {
			passSecret += "*";
		}
		drawInput.drawString(textPosX, passPosY, passSecret, Color.white);
		checkIfExe();
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
				state.setStatus(Status.CREATENEW);
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
	
}
