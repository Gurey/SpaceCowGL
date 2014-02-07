package spacecow.gui;

import org.newdawn.slick.Color;

import spacecow.engine.DrawText;
import spacecow.engine.DrawText.Alignment;
import spacecow.engine.Game;
import spacecow.engine.GameObject;
import spacecow.engine.TextureHandler;
import spacecow.objects.Asteroid;
import spacecow.objects.GameObjectHandler;
import spacecow.objects.Player;


public class HowToPlay {

	private GameObjectHandler objectHandler;
	private Player player;
	private DrawText drawInfo;
	private TextureHandler textureHandler;
	
	public HowToPlay(GameObjectHandler gameObjectHandler, Player player, TextureHandler textureHandler) {
		this.objectHandler = gameObjectHandler;
		this.player = player;
		this.drawInfo = new DrawText(35, Alignment.RIGHT);
		this.textureHandler = textureHandler;
	}
	
	public void update(){
		objectHandler.update();
		player.update();
		for (GameObject go : objectHandler.getGameObjectArray()) {
			if (!(go instanceof Asteroid)) {
				drawInfo.drawString(go.getX(), go.getY(), "GOOD!->", Color.green);
			}
			else drawInfo.drawString(go.getX(), go.getY(), "BAD!->", Color.red);
		}
		Color.white.bind();
//		textureHandler.drawTexture(textureHandler.getArrowKeys(), 100, Game.dHeight-textureHandler.getArrowKeys().getTextureHeight());
//		textureHandler.drawTexture(textureHandler.getShiftKey(), 500, Game.dHeight-textureHandler.getShiftKey().getTextureHeight());
	}
	
}
