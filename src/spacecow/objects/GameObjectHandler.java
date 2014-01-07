package spacecow.objects;

import java.util.ArrayList;

import org.lwjgl.Sys;

import spacecow.buffs.Magnet;
import spacecow.buffs.SuperSpeed;
import spacecow.engine.Game;
import spacecow.engine.GameObject;
import spacecow.engine.Score;
import spacecow.engine.TextureHandler;

public class GameObjectHandler {

	//GameObjects: Cookie, ScoreMultiplyer
	private  ArrayList<GameObject> gameObjectArray = new ArrayList<>();
	private  ArrayList<GameObject> gameObjectRemove = new ArrayList<>();
	//Backgroundstars
	private  ArrayList<Star> starsArray = new ArrayList<>();

	private Score score;
	private TextureHandler texHandler;
	private SuperSpeed superSpeed;
	private Player player;
	private Magnet magnet;

	long nextStarBuff, nextScoreMultiplyer, nextCookie, nextAsteroid, nextMagnet;

	public GameObjectHandler(Score score, TextureHandler texHandler, SuperSpeed superSpeed, Player player){
		this.nextStarBuff=Sys.getTime()+50;
		this.nextCookie=Sys.getTime()+15000;
		this.nextScoreMultiplyer=Sys.getTime()+1000;
		this.nextAsteroid=Sys.getTime()+1000;
		this.nextMagnet=(long) (Sys.getTime()+Math.random()*15000);
		this.score = score;
		this.texHandler = texHandler;
		this.superSpeed = superSpeed;
		this.player = player;
		createStars(80);
	}

	//Updates the object, remove all the objects, clears the remove Array, move all the other objects and then check if its time to create new objects 
	public void update(){
		removeObjectsFromArray();
		gameObjectRemove.clear();
		moveAllObjects();
		checkIfNewObjects();

	}
	//Going through the arrays of objects to move and print them on the screen
	//if they are colliding with player they are added to the remove array.
	private void moveAllObjects(){
		if (StarBuff.getAddStar()>=10) {
			StarBuff.setAddStar(0);
			starsArray.add(new Star(score, texHandler, -10, superSpeed));
		}
		for (Star st : starsArray) {
			st.move();
		}
		for (GameObject go : gameObjectArray) {
			go.move();
			if (go.isRotating()){
				texHandler.drawRotatingTexture(go.getObjTex(), go.getX(), go.getY(), go.getRotation());
				go.setRotation(go.getRotation()+go.getRotationSpeed());
			}
			else texHandler.drawTexture(go.getObjTex(), go.getX(), go.getY());
			if (go.colliding()) {
				go.collisionAction();
				gameObjectRemove.add(go);
			}
			else if (go.getY()>Game.dHeight) {
				gameObjectRemove.add(go);
			}
		}
	}
	//removing objects that are no longer on screen.
	private void removeObjectsFromArray(){
		for (GameObject go : gameObjectRemove) {
			gameObjectArray.remove(go);
		}
	}
	//Checks if its time to create new objects.
	private void checkIfNewObjects(){
		//new Cookie, new cookies are created in a random range within 0.5 to 15 seconds.
		if (Sys.getTime()>nextCookie) {
			this.gameObjectArray.add(new Cookie(texHandler.getCookieTex(),score, player, magnet));
			nextCookie = (long) (Sys.getTime()+500+(Math.random()*14500));
		}
		//new ScoreMulti, new score multi are created within a range of 0.2 to 2.7 seconds minus the score multi*5
		if (Sys.getTime()>nextScoreMultiplyer) {
			this.gameObjectArray.add(new ScoreMultiplyer(texHandler.getPlusTex(),score, player));
			nextScoreMultiplyer = (long) (Sys.getTime()+200+(Math.random()*2500)-(score.getScoreMulti()*5));
		}
		//new StarBuff, new StarBuffs are created within the range of 0.5 seconds.
		if (Sys.getTime()>nextStarBuff) {
			this.gameObjectArray.add(new StarBuff(texHandler.getStarBuffTex(),score, player));
			nextStarBuff = (long) (Sys.getTime()+(Math.random()*500));
		}
		//new Asteroid, created within the range of 0.5 to 1.8 seconds minus scoreMulti*5.
		if (Sys.getTime()>nextAsteroid) {
			this.gameObjectArray.add(new Asteroid(texHandler.getAsteroidTex(), score, player));
			nextAsteroid = (long) (Sys.getTime()+300+(Math.random()*1500-score.getScoreMulti()*5));
			//The lowest spawn rate is every 300 milliseconds.
			if (nextAsteroid<Sys.getTime()+300) {
				nextAsteroid=Sys.getTime()+300;
			}
		}
		//new Magnet, created within the range of 0.5 to 6.5 seconds.
		if (Sys.getTime()>nextMagnet) {
			this.gameObjectArray.add(new MagnetObj(texHandler.getMagnet(), score, player, magnet));
			nextMagnet = (long) (Sys.getTime()+500+(Math.random()*6000));
		}
	}
	//Created initial star count
	private void createStars(int numberOfStars){
		for (int i = 0; i < numberOfStars; i++) {
			starsArray.add(new Star(score, texHandler, superSpeed));
		}
	}
	public void setMagnet(Magnet magnet){
		this.magnet = magnet;
	}
	public ArrayList<Star> getStarsArray() {
		return starsArray;
	}
	public ArrayList<GameObject> getGameObjectArray() {
		return gameObjectArray;
	}
	public ArrayList<GameObject> getGameObjectRemove() {
		return gameObjectRemove;
	}
}