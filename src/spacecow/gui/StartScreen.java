package spacecow.gui;

import spacecow.engine.Game;
import spacecow.engine.GameState.Status;

public class StartScreen extends Menu{

	public StartScreen(Game game) {
		super(game);
		addMenuObject("Login", false, false, false);
		addMenuObject("Create new account", false, false, false);
	}

	public void checkIfExe(){
		switch (getPointer().getPointerState()) {
		case 1:
			getGame().getGameState().setStatus(Status.LOGON);
			break;
		case 2:
			getGame().getGameState().setStatus(Status.CREATENEW);
			break;
		default:
			break;
		}
	}
}
