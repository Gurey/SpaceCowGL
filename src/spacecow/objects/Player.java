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
	private int speed=8;
	private Rectangle pRectangle;
	private TextureHandler texHandler;
	private SuperSpeed superSpeed;
	
		
	public Player(TextureHandler texHandler, SuperSpeed superSpeed){
		this.x = Game.dWidth/2;
		this.y = Game.dHeight/2;
		
		this.neutralCow = texHandler.getCowTex();
		this.speedX = 0;
		this.speedY = 0;
		this.pRectangle = new Rectangle(this.x, this.y, this.neutralCow.getWidth(), this.neutralCow.getHeight());
		this.superSpeed=superSpeed;
		this.texHandler=texHandler;
	}
	
	public void move(float x, float y){
			this.x += this.speedX*x*Rush.getInstance().getVel();
			this.y += this.speedY*y;
			if (this.x > Game.dWidth-this.neutralCow.getTextureWidth()) 
				this.x = Game.dWidth-this.neutralCow.getTextureWidth();
			if (this.x < 0) 
				this.x = 0;
			if (this.y > Game.dHeight-this.neutralCow.getTextureHeight()) 
				this.y = Game.dHeight-this.neutralCow.getTextureHeight();
			if (this.y < 0) 
				this.y = 1;
			
	}
	public void update(){
		getInput();
		this.pRectangle.setBounds(this.x, this.y, this.neutralCow.getTextureWidth(), this.neutralCow.getTextureHeight());
		
		this.speedX = (float) (this.speedX*0.93);
		this.speedY = (float) (this.speedY*0.93);
		
		if (this.up) {
			move(0,-1); //move up
		}
		if (this.down) {
			move(0,1); //move down			
		}
		if (this.left) {
			move(-1,0); //move left
		}
		if (this.right) {
			move(1,0); //move right
		}
		if (this.speedX<0.1) {
			this.speedX=0;
		}
		if (this.speedY<0.1) {
			this.speedY=0;
		}
		texHandler.drawTexture(neutralCow, this.x, this.y);
	}
	public void getInput(){
//		this.speedX = (float) (8+Rush.getInstance().getVel());
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Rush.getInstance().isAvailable()) {
			Thread rushThread = new Thread(Rush.getInstance());
			rushThread.start();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			this.up=true; //move up
			this.down=false;
			this.speedY = speed;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			this.up=false; //move down
			this.down=true;
			this.speedY = speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			this.right=false; //move left
			this.left=true;
			this.speedX=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			this.right=true; //move right
			this.left=false;
			this.speedX=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_1) && (superSpeed.isAvailable())){
			Thread superSpeedThread = new Thread(superSpeed);
			superSpeedThread.start();
			System.out.println("press");
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
	
	
}
