package spacecow.objects;

import java.util.ArrayList;

import org.lwjgl.Sys;

import spacecow.buffs.SuperSpeed;
import spacecow.engine.Game;
import spacecow.engine.GameObject;
import spacecow.engine.Score;
import spacecow.engine.TextureHandler;

public class GameObjectHandler {

	//GameObjects: Cookie, ScoreMultiplyer
	private  ArrayList<GameObject> gameObjectArray = new ArrayList<>();
	private  ArrayList<GameObject> gameObjectRemove = new ArrayList<>();
	//Score multiplyer
	private  ArrayList<ScoreMultiplyer> smArray = new ArrayList<>(); 
	private  ArrayList<ScoreMultiplyer> smRemove = new ArrayList<>();
	//StarBuff
	private  ArrayList<StarBuff> sbArray = new ArrayList<>();
	private  ArrayList<StarBuff> sbRemove = new ArrayList<>();
	//Backgroundstars
	private  ArrayList<Star> starsArray = new ArrayList<>();
	
	private Score score;
	private TextureHandler texHandler;
	private SuperSpeed superSpeed;
	private Player player;
	
	long nextStarBuff, nextScoreMultiplyer, nextCookie;
	
	public GameObjectHandler(Score score, TextureHandler texHandler, SuperSpeed superSpeed, Player player){
		this.nextStarBuff=Sys.getTime()+50;
		this.nextCookie=Sys.getTime()+30000;
		this.nextScoreMultiplyer=Sys.getTime()+1000;
		this.score = score;
		this.texHandler = texHandler;
		this.superSpeed = superSpeed;
		this.player = player;
		createStars(150);
	}
	
	public ArrayList<ScoreMultiplyer> getSmArray() {
		return smArray;
	}
	public ArrayList<ScoreMultiplyer> getSmRemove() {
		return smRemove;
	}
	public ArrayList<StarBuff> getSbArray() {
		return sbArray;
	}
	public ArrayList<StarBuff> getSbRemove() {
		return sbRemove;
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
	public void update(){
		removeObjectsFromArray();
		moveAllObjects();
		checkIfNewObjects();
		
	}
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
	
	private void removeObjectsFromArray(){
		for (GameObject go : gameObjectRemove) {
			gameObjectArray.remove(go);
		}
	}
	private void checkIfNewObjects(){
		//new Cookie
		if (Sys.getTime()>nextCookie) {
			this.gameObjectArray.add(new Cookie(texHandler.getCookieTex(),score, player));
			nextCookie = (long) (Sys.getTime()+500+(Math.random()*30000));
		}
		//new ScoreMulti
		if (Sys.getTime()>nextScoreMultiplyer) {
			this.gameObjectArray.add(new ScoreMultiplyer(texHandler.getPlusTex(),score, player));
			nextScoreMultiplyer = (long) (Sys.getTime()+500+(Math.random()*5000)-(score.getScoreMulti()*10));
		}
		//new StarBuff
		if (Sys.getTime()>nextStarBuff) {
			this.gameObjectArray.add(new StarBuff(texHandler.getStarBuffTex(),score, player));
			nextStarBuff = (long) (Sys.getTime()+(Math.random()*500));
		}
	}
	private void createStars(int numberOfStars){
		for (int i = 0; i < numberOfStars; i++) {
			starsArray.add(new Star(score, texHandler, superSpeed));
		}
	}
}