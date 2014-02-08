package spacecow.gui;

import org.newdawn.slick.Color;

import spacecow.buffs.Magnet;
import spacecow.engine.DrawText;
import spacecow.engine.DrawText.Alignment;
import spacecow.engine.Game;
import spacecow.engine.GameObject;
import spacecow.engine.TextureHandler;
import spacecow.objects.Asteroid;
import spacecow.objects.Cookie;
import spacecow.objects.GameObjectHandler;
import spacecow.objects.MagnetObj;
import spacecow.objects.Player;
import spacecow.objects.ScoreMultiplyer;
import spacecow.objects.StarBuff;


public class HowToPlay {

	private GameObjectHandler objectHandler;
	private Player player;
	private DrawText drawInfo;
	private TextureHandler textureHandler;
	private Magnet magnet;
	
	public HowToPlay(GameObjectHandler gameObjectHandler, Player player, TextureHandler textureHandler, Magnet magnet) {
		this.objectHandler = gameObjectHandler;
		this.player = player;
		this.drawInfo = new DrawText(35, Alignment.RIGHT);
		this.textureHandler = textureHandler;
		this.magnet = magnet;
	}
	
	public void update(){
		objectHandler.update();
		setPositions();
		player.update();
//		for (GameObject go : objectHandler.getGameObjectArray()) {
//			if (!(go instanceof Asteroid)) {
//				drawInfo.drawString(go.getX(), go.getY(), "GOOD!->", Color.green);
//			}
//			else drawInfo.drawString(go.getX(), go.getY(), "BAD!->", Color.red);
//		}
//		Color.white.bind();
//		textureHandler.drawTexture(textureHandler.getArrowKeys(), 100, Game.dHeight-textureHandler.getArrowKeys().getTextureHeight());
//		textureHandler.drawTexture(textureHandler.getShiftKey(), 500, Game.dHeight-textureHandler.getShiftKey().getTextureHeight());
	}
	
	private void setPositions(){
		for (GameObject go : objectHandler.getGameObjectArray()) {
			if (go instanceof StarBuff && magnet.isAvailable()) go.setX(Game.dWidth/10);
			else if(go instanceof Cookie) go.setX(Game.dWidth/4);
			else if(go instanceof Asteroid) go.setX((float) (Game.dWidth/2));
			else if(go instanceof MagnetObj) go.setX((float) (Game.dWidth/1.5));
			else if(go instanceof ScoreMultiplyer) go.setX((float) (Game.dWidth/1.2));
		}
	}
	
}
