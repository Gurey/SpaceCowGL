package spacecow.objects;


import org.lwjgl.input.Keyboard;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

import spacecow.buffs.Rush;
import spacecow.buffs.SuperSpeed;
import spacecow.engine.Game;
import spacecow.engine.TextureHandler;

public class Player {

	private float x, y, speedX, speedY;
	private Texture neutralCow;
	private boolean up,down, right,left;
	private int speed=10;
	private Rectangle pRectangle;
	private TextureHandler texHandler;
	private SuperSpeed superSpeed;
	private Rush rush;


	public Player(TextureHandler texHandler, Rush rush){

		this.neutralCow = texHandler.getCowTex();
		this.x = Game.dWidth/2-(neutralCow.getTextureWidth()/2);
		this.y = Game.dHeight/2-(neutralCow.getHeight()/2);
		this.speedX = 0;
		this.speedY = 0;
		this.pRectangle = new Rectangle(this.x, this.y, this.neutralCow.getImageWidth(), this.neutralCow.getImageHeight()-1000);
		this.texHandler=texHandler;
		this.rush = rush;
	}
	//Moves the Player
	public void move(float x, float y){
		this.x += x*rush.getVel();
		this.y += this.speedY*y;
		if (this.x > Game.dWidth-this.pRectangle.getWidth()) 
			this.x = Game.dWidth-this.pRectangle.getWidth();
		if (this.x < 0) 
			this.x = 0;
		if (this.y > Game.dHeight-this.pRectangle.getHeight()) 
			this.y = Game.dHeight-this.pRectangle.getHeight();
		if (this.y < 0) 
			this.y = 1;

	}
	//takes in the input from the Keyboard, moves the player, if the player is barely moving it sets the speed to 0, then print out the rectangle and draws the texture.
	public void update(){
		getInput();

		this.speedX = (float) (this.speedX*0.93);
		this.speedY = (float) (this.speedY*0.93);

		if (this.up) {
			move(0,-1); //move up
		}
		if (this.down) {
			move(0,1); //move down			
		}
		if (this.left) {
			move(this.speedX,0); //move left
		}
		if (this.right) {
			move(this.speedX,0); //move right
		}
		if (this.speedX<0.1 && this.speedX>-0.1) {
			this.speedX=0;
			this.left=false;
			this.right=false;
		}
		if (this.speedY<0.1) {
			this.speedY=0;
			this.up=false;
			this.down=false;
		}
		this.pRectangle.setBounds(this.x, this.y, this.neutralCow.getImageWidth(), (float)this.neutralCow.getImageHeight());

	}
	public void render() {
		texHandler.drawRotatingTexture(neutralCow, this.x, this.y-neutralCow.getImageHeight()/2, (float) (this.speedX*3));
	}
	//Check if there are any input from the Keyboard.
	public void getInput(){
		//Checks if "Left Shift" is pressed and if Rush is available to execute.
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && rush.isAvailable()) {
			//Init a new Rush.
			rush.initRush();
		}
		//enables the Player to move up
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			this.up=true; //move up
			this.down=false;
			this.speedY = speed;
		}
		//enables the player to move down
		else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			this.up=false; //move down
			this.down=true;
			this.speedY = speed;
		}
		//Enables the player to move left
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			this.right=false; //move left
			this.left=true;
			this.speedX-=1.2;
		}
		//Enables the player to move right.
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			this.right=true; //move right
			this.left=false;
			this.speedX+=1.2;
		}
		//Checks if "key 1" is pressed and if superSpeed is available.
		if (Keyboard.isKeyDown(Keyboard.KEY_1) && (superSpeed.isAvailable())){
			Thread superSpeedThread = new Thread(superSpeed); //Init superspeed
			superSpeedThread.start();
		}

	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getVel() {
		return speedX;
	}

	public void setVel(float vel) {
		this.speedX = vel;
	}
	public Texture getTexture(){
		return this.neutralCow;
	}
	public Rectangle getpRectangle() {
		return this.pRectangle;
	}
	public Rush getRush() {
		return rush;
	}
	public void setRush(Rush rush) {
		this.rush = rush;
	}
	
	public void setNeutralCow(Texture neutralCow) {
		this.neutralCow = neutralCow;
	}

}
