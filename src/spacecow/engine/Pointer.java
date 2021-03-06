package spacecow.engine;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public class Pointer {
	
	private boolean keyRealeased;
	private int pointerState, pointerStateMax;
	private float rotation, startPosX, startPosY, padding;
	private TextureHandler textureHandler;
	private Texture pointerTex;
	
	public Pointer(float startPosX, float startPosY, float gapBetweenPointerStates, int pointerMax,float rotation, TextureHandler textureHandler){
		this.pointerStateMax=pointerMax;
		this.pointerState = 1;
		this.rotation=rotation;
		this.textureHandler = textureHandler;
		this.pointerTex = textureHandler.getStarBuffTex();
		this.startPosY = startPosY;
		this.startPosX = startPosX;
		this.padding = gapBetweenPointerStates;
	}
	
	public void updatePointerState(){
		if ((Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_TAB))  && keyRealeased) {
			pointerState++;
			keyRealeased=false;
			if (pointerState>pointerStateMax) pointerState = 1;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_UP)&& keyRealeased){
			pointerState--;
			keyRealeased=false;
			if(pointerState<1) pointerState=pointerStateMax;
		}
		else if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_TAB) && !keyRealeased) {
			keyRealeased = true;
		}
		drawPointer();
	}
	
	private void drawPointer(){
		float yPos = startPosY;
		yPos = startPosY+((pointerState-1)*padding);
		textureHandler.drawRotatingTexture(pointerTex, startPosX, yPos, rotation);
		rotation += 1;
	}
	
	public int getPointerState(){
		return pointerState;
	}
	public void setPointerState(int state){
		this.pointerState = state;
	}

	public boolean isKeyRealeased() {
		return keyRealeased;
	}

	public int getPointerStateMax() {
		return pointerStateMax;
	}

	public float getRotation() {
		return rotation;
	}

	public float getStartPosX() {
		return startPosX;
	}

	public float getStartPosY() {
		return startPosY;
	}

	public float getPadding() {
		return padding;
	}

	public TextureHandler getTextureHandler() {
		return textureHandler;
	}

	public Texture getPointerTex() {
		return pointerTex;
	}

	public void setKeyRealeased(boolean keyRealeased) {
		this.keyRealeased = keyRealeased;
	}

	public void setPointerStateMax(int pointerStateMax) {
		this.pointerStateMax = pointerStateMax;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setStartPosX(float startPosX) {
		this.startPosX = startPosX;
	}

	public void setStartPosY(float startPosY) {
		this.startPosY = startPosY;
	}

	public void setPadding(float padding) {
		this.padding = padding;
	}

	public void setTextureHandler(TextureHandler textureHandler) {
		this.textureHandler = textureHandler;
	}

	public void setPointerTex(Texture pointerTex) {
		this.pointerTex = pointerTex;
	}
	
}
