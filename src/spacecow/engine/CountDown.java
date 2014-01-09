package spacecow.engine;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;

import spacecow.objects.Star;

public class CountDown {

	private long time=Sys.getTime();
	private int countdownState=3;
	private Texture numberTex; 
	ArrayList<Star> starArray;
	TextureHandler texHandler;
	public CountDown(ArrayList<Star> starArray, TextureHandler texHandler){
		this.starArray=starArray;
		this.texHandler = texHandler;
	}
	//Moves all the stars in the background and count down from 3 to 1 by printing out a Texture on the middle of the screen.
	public void countDown(){
		for (Star star : starArray) {
			star.move();
		}
		if (countdownState==3) {
			numberTex = texHandler.getNumber3tex();
			if (Sys.getTime()>time+1000){
				countdownState=2;
				time=Sys.getTime();
			}
		}
		else if (countdownState==2) {
			numberTex = texHandler.getNumber2tex();
			if (Sys.getTime()>time+1000){
				countdownState=1;
				time=Sys.getTime();
			}
		}
		else if (countdownState==1) {
			numberTex = texHandler.getNumber1tex();
			if (Sys.getTime()>time+1000){
				countdownState=0;
			}
		}
		texHandler.drawTexture(numberTex, (Game.dWidth/2)-(numberTex.getTextureWidth()/2), (Game.dHeight/2)-(numberTex.getTextureHeight()/2));
	}
	public int getCountdownState(){
		return countdownState;
	}
	public void setCountDownState(int state){
		int stateToSet = state;
		if (stateToSet<3) {
			stateToSet = 3;
		}
		this.countdownState=stateToSet;
	}
	
}
