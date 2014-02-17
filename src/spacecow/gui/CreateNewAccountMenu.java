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

public class CreateNewAccountMenu {

	private ArrayList<Star> starArrayList;
	private TextureHandler textureHandler;
	private GameState state;
	private DrawText drawInfo;
	private DrawText drawInput;
	private String accountName, password, passSecret, password2, backString, eMail;
	private float textPosX, accPosY, passPosY, pass2PosY, backYPos, mailPosY;
	private KeyboadTextInput input;
	private Pointer pointer;
	private boolean stateChanged;
	private ServerConnection connection;
	
	public CreateNewAccountMenu(ArrayList<Star> starArrayList, TextureHandler textureHandler, GameState state, ServerConnection connection){
		this.setConnection(connection);
		this.setStarArrayList(starArrayList);
		this.setTextureHandler(textureHandler);
		this.setState(state);
		this.drawInfo =  new DrawText(50, Alignment.RIGHT);
		this.drawInput = new DrawText(50, Alignment.LEFT);
		this.textPosX = Game.dWidth/2;
		this.accPosY = (Game.dHeight/2)-250;
		this.passPosY = (Game.dHeight/2)-150;
		this.pass2PosY = (Game.dHeight/2)-50;
		this.mailPosY = (Game.dHeight/2)+50;
		this.backYPos = (Game.dHeight/2)+150;
		this.password = "";
		this.password2 = "";
		this.accountName = "";
		this.eMail = "";
		this.backString = "<<<Back  ";
		this.input = new KeyboadTextInput();
		this.pointer = new Pointer(350,accPosY, 100, 5, 1, textureHandler);
	}
	
	public void update(){
		for (Star star : starArrayList) {
			star.move();
		}
		pointer.updatePointerState();
		drawInfo.drawString(textPosX, accPosY, "Name: ", Color.white);
		drawInfo.drawString(textPosX, passPosY, "Password: ", Color.white);
		drawInfo.drawString(textPosX, pass2PosY, "Repeat: ", Color.white);
		drawInfo.drawString(textPosX, mailPosY, "eMail: ", Color.white);
		drawInfo.drawString(textPosX, backYPos, backString, Color.white);
		getInput();
		drawInput.drawString(textPosX, accPosY, accountName, Color.white);
		passSecret = "";
		for (int i = 0; i < password.length(); i++) {
			passSecret += "*";
		}
		drawInput.drawString(textPosX, passPosY, passSecret, Color.white);
		passSecret = "";
		for (int i = 0; i < password2.length(); i++) {
			passSecret += "*";
		}
		drawInput.drawString(textPosX, pass2PosY, passSecret, Color.white);
		drawInput.drawString(textPosX, mailPosY, eMail, Color.white);
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
			break;
		case 3:
			password2 = input.getInput(drawInput, password2,15);
			password2 = password2.trim();
			break;
		case 4:
			eMail = input.getInput(drawInput, eMail,35);
			eMail =  eMail.trim();
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
			case 3:
			case 4:
				
				if (!accountName.isEmpty() && (password.length() > 5 && password.equals(password2)) && checkEmail()) {
				System.out.println("HEJ! :D");
				Json json = new Json();
				json.setType("CREATENEW");
				json.setName(accountName);
				json.setPassword(password);
				json.seteMail(eMail);
				connection.send(new Gson().toJson(json, Json.class));
				}
				stateChanged = false;
				break;
			case 5:
				state.setStatus(Status.LOGON);
				stateChanged = false;
				break;
			default:
				break;
			}
		}
	}
	private boolean checkEmail(){
		System.out.println("Checking Email: "+"'"+eMail+"'");
		boolean eMailState = false;
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		eMailState = eMail.matches(emailreg);
		System.out.println("True email: "+eMailState);
		return eMailState;
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

	public ServerConnection getConnection() {
		return connection;
	}

	public void setConnection(ServerConnection connection) {
		this.connection = connection;
	}
	
}
