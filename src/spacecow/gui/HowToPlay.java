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
	private String message;
	
	public HowToPlay(GameObjectHandler gameObjectHandler, Player player, TextureHandler textureHandler, Magnet magnet) {
		this.objectHandler = gameObjectHandler;
		this.player = player;
		this.drawInfo = new DrawText(35, Alignment.CENTER);
		this.textureHandler = textureHandler;
		this.magnet = magnet;
		this.message = "";
	}
	
	public void update(){
		objectHandler.update();
		setPositions();
		player.update();
		drawInfo();
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
	private void drawInfo(){
		for (GameObject go : objectHandler.getGameObjectArray()) {
			if (go.colliding()) {
			if (go instanceof StarBuff && magnet.isAvailable()) message = "Stars gives you 100 x ScoreMultiplyer x ScoreMultiplyer points";
			else if(go instanceof Cookie) message = "Cookies adds 10 seconds on the time";
			else if(go instanceof Asteroid) message = "Ateroids removes 10 seconds from the timer";
			else if(go instanceof MagnetObj) message = "Draws all the stars to you";
			else if(go instanceof ScoreMultiplyer) message = "Adds +1 to the score multi, decreases after 5 seconds if you dont catch a new";
		}
		}
		drawInfo.drawString(Game.dWidth/2, 100, message, Color.white);
	}
}
