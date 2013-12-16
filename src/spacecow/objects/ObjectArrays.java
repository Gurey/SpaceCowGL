package spacecow.objects;

import java.util.ArrayList;

import spacecow.engine.GameObject;

public class ObjectArrays {

	//ObjectArray
	private static ArrayList<GameObject> GameObjectArray = new ArrayList<>();
	private static ArrayList<GameObject> GameObjectRemove = new ArrayList<>();
	//Score multiplyer
	private static ArrayList<ScoreMultiplyer> smArray = new ArrayList<>(); 
	private static ArrayList<ScoreMultiplyer> smRemove = new ArrayList<>();
	//StarBuff
	private static ArrayList<StarBuff> sbArray = new ArrayList<>();
	private static ArrayList<StarBuff> sbRemove = new ArrayList<>();
	//Backgroundstars
	private static ArrayList<Star> starsArray = new ArrayList<>();
	
	public static ArrayList<ScoreMultiplyer> getSmArray() {
		return smArray;
	}
	public static ArrayList<ScoreMultiplyer> getSmRemove() {
		return smRemove;
	}
	public static ArrayList<StarBuff> getSbArray() {
		return sbArray;
	}
	public static ArrayList<StarBuff> getSbRemove() {
		return sbRemove;
	}
	public static ArrayList<Star> getStarsArray() {
		return starsArray;
	}
	public static ArrayList<GameObject> getGameObjectArray() {
		return GameObjectArray;
	}
	public static ArrayList<GameObject> getGameObjectRemove() {
		return GameObjectRemove;
	}
	
}
