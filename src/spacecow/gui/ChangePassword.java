package spacecow.gui;

import com.google.gson.Gson;

import spacecow.engine.Game;
import spacecow.engine.GameState.Status;
import spacecow.serverconnection.Json;

public class ChangePassword extends Menu {

	public ChangePassword(Game game) {
		super(game);
		addMenuObject("New Password: ", true, true, true);
		addMenuObject("New Password again: ", true, true, true);
		addMenuObject("Send!", false, false,false);
		addMenuObject("<- Back", false, false,false);
		setTitle("Change Password");
		setMessage("Enter new password two times!");
	}
	
	@Override
	public void checkIfExe() {
		switch (getPointer().getPointerState()) {
		case 3:
			String firstPass = getMenuObjects().get(0).getMenuInput();
			String secondPass = getMenuObjects().get(1).getMenuInput();
			if (firstPass.equals(secondPass) && firstPass.length()>=5) {
				setMessage("Sending request!");
				Json pass = new Json();
				pass.setType("CHANGEPASS");
				pass.setPassword(firstPass);
				getGame().getServerConnection().send(new Gson().toJson(pass, Json.class));
			}
			else setMessage("The passwords are not equal, or to short (5 characters minimum)");
			break;
		case 4:
			getGame().getGameState().setStatus(Status.MENU);
			break;
		default:
			break;
		}
	}

}
