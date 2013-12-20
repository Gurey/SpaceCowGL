package spacecow.objects;

import java.util.ArrayList;

import org.lwjgl.Sys;

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
	
	long nextStarBuff, nextScoreMultiplyer, nextCookie;
	private static GameObjectHandler instance=null;
	public static GameObjectHandler getInstance(){
		if (instance == null) {
			instance= new GameObjectHandler();
		}
		return instance;
	}
	private GameObjectHandler(){
		this.nextStarBuff=Sys.getTime()+50;
		this.nextCookie=Sys.getTime()+30000;
		this.nextScoreMultiplyer=Sys.getTime()+1000;
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
		for (Star st : starsArray) {
			st.move();
		}
		for (GameObject go : gameObjectArray) {
			go.move();
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
			this.gameObjectArray.add(new Cookie(TextureHandler.getInstance().getCookieTex()));
			nextCookie = (long) (Sys.getTime()+500+(Math.random()*30000));
		}
		//new ScoreMulti
		if (Sys.getTime()>nextScoreMultiplyer) {
			this.gameObjectArray.add(new ScoreMultiplyer(TextureHandler.getInstance().getPlusTex()));
			nextScoreMultiplyer = (long) (Sys.getTime()+500+(Math.random()*5000)-(Score.getScoreMulti()*10));
		}
		//new StarBuff
		if (Sys.getTime()>nextStarBuff) {
			this.gameObjectArray.add(new StarBuff(TextureHandler.getInstance().getStarBuffTex()));
			nextStarBuff = (long) (Sys.getTime()+(Math.random()*50));
		}
	}
	
}
