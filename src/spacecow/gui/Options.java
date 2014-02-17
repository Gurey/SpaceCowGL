package spacecow.gui;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import com.google.gson.Gson;

import spacecow.engine.Game;
import spacecow.engine.TextureHandler;
import spacecow.engine.GameState.Status;
import spacecow.objects.Player;
import spacecow.serverconnection.Json;

public class Options extends Menu{
	
	private int skinID;
	private int skinMax;
	private TextureHandler textureHandler;
	private Player player;
	
	public Options(Game game) {
		super(game);
		addMenuObject("Change skin: ", false, false);
		addMenuObject("Fullscreen: ", true, false);
		addMenuObject("Change password", false, false);
		addMenuObject("<- Back: ", false, false);
		setTitle("Options");
		skinID = 1;
		skinMax = 4;
		textureHandler = getGame().getTexHandler();
		player = getGame().getPlayer();
	}

	@Override
	public void checkIfExe(){
		switch (getPointer().getPointerState()) {
		case 1:
			skinID++;
			if (skinID > skinMax) skinID = 1;
			setSkin(skinID);
			break;
		case 2:
			getGame().getdConfig().setDisplayMode(Game.dWidth, Game.dHeight, !Display.isFullscreen());
			break;
		case 3:
			getGame().getGameState().setStatus(Status.CHANGEPASS);
			break;
		case 4:
			getGame().getGameState().setStatus(Status.MENU);
			getPointer().setPointerState(1);
			break;
		default:
			break;
		}
	}
	
	public void setSkin(int skinID){
		Texture tex = null;
		switch (skinID) {
		case 1:
			tex = textureHandler.getCowTex();
			break;
		case 2:
			tex = textureHandler.getCowsad();
			break;
		case 3:
			tex = textureHandler.getCowCool();
			break;
		case 4:
			tex = textureHandler.getDragonCow();
			break;
		default:
			break;
		}
		player.setNeutralCow(tex);
		Json skin = new Json();
		skin.setType("SKIN");
		skin.setSkinID(skinID);
		getGame().getServerConnection().send(new Gson().toJson(skin, Json.class));
	}
}
