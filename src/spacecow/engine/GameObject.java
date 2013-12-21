package spacecow.engine;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

import spacecow.objects.GameObjectHandler;
import spacecow.objects.Player;

public class GameObject {

		private Texture objTex;
		private Rectangle objRect;
		private float x,y;
		private float speed = 8,rotation=0,rotationSpeed;
		private boolean isRotating = false;
		private boolean isMagnetic = true;
		
		
		public GameObject(Texture tex) {
			this.objTex = tex;
			this.y = -10-objTex.getImageHeight();
			this.x = (float)Math.random()*Game.dWidth-(objTex.getTextureWidth()/2);
			this.objRect = new Rectangle(this.x, this.y, objTex.getWidth(), objTex.getHeight());
		}
		
		public void move(){
			this.y += this.speed;
			if (isRotating){
				TextureHandler.getInstance().drawRotatingTexture(this.objTex, x, y, rotation);
				rotation+=rotationSpeed;
			}
			else TextureHandler.getInstance().drawTexture(this.objTex, this.x, this.y);
			this.objRect.setBounds(this.x, this.y, objTex.getTextureWidth(), objTex.getTextureHeight());
			if (colliding()) {
				this.collisionAction();
				GameObjectHandler.getInstance().getGameObjectRemove().add(this);
			}
			else if (this.getY()>Game.dHeight) {
					GameObjectHandler.getInstance().getGameObjectRemove().add(this);
				}
			}
		
		private boolean colliding(){
			boolean collision = UnitCollission.isColliding(objRect, Player.getInstance().getpRectangle());
			return collision;
		}
		public void collisionAction(){
			
		}

		public Texture getObjTex() {
			return objTex;
		}

		public void setObjTex(Texture objTex) {
			this.objTex = objTex;
		}

		public Rectangle getObjRect() {
			return objRect;
		}

		public void setObjRect(Rectangle objRect) {
			this.objRect = objRect;
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

		public float getSpeed() {
			return speed;
		}

		public void setSpeed(float speed) {
			this.speed = speed;
		}

		public float getRotation() {
			return rotation;
		}

		public void setRotation(float rotation) {
			this.rotation = rotation;
		}

		public float getRotationSpeed() {
			return rotationSpeed;
		}

		public void setRotationSpeed(float rotationSpeed) {
			this.rotationSpeed = rotationSpeed;
		}

		public boolean isRotating() {
			return isRotating;
		}

		public void setRotating(boolean isRotating) {
			this.isRotating = isRotating;
		}

		public boolean isMagnetic() {
			return isMagnetic;
		}

		public void setMagnetic(boolean isMagnetic) {
			this.isMagnetic = isMagnetic;
		}
}
