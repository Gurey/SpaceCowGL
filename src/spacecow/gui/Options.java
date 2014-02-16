package spacecow.gui;

import org.lwjgl.opengl.Display;

import spacecow.engine.Game;
import spacecow.engine.GameState.Status;

public class Options extends Menu{

	public Options(Game game) {
		super(game);
		addMenuObject("Change skin: ", false, false);
		addMenuObject("Fullscreen: ", true, false);
		addMenuObject("<- Back: ", false, false);
		setTitle("Options");
	}

	@Override
	public void checkIfExe(){
		switch (getPointer().getPointerState()) {
		case 1:
			
			break;
		case 2:
			getGame().getdConfig().setDisplayMode(Game.dWidth, Game.dHeight, !Display.isFullscreen());
			break;
		case 3:
			getGame().getGameState().setStatus(Status.MENU);
			getPointer().setPointerState(1);
			break;
		default:
			break;
		}
	}
	
}
