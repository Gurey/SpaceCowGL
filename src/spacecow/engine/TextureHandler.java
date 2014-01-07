package spacecow.engine;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public final class TextureHandler{
	
	
	private Texture number1tex;
	private Texture number2tex;
	private Texture number3tex;
	
	private Texture star1Tex;
	private Texture star2Tex;
	private Texture star3Tex;
	private Texture star4Tex;
	private Texture star5Tex;
	
	private Texture cowTex;
	private Texture starBuffTex;
	private Texture cookieTex;
	private Texture plusTex;
	private Texture asteroidTex;
	private Texture magnet;
	
	public TextureHandler() {
		
		 number1tex = loadTexture("res/number1.png");
		 number2tex = loadTexture("res/number2.png");
		 number3tex = loadTexture("res/number3.png");
		
		 star1Tex = loadTexture("/res/Star1.png");
		 star2Tex = loadTexture("/res/Star2.png");
		 star3Tex = loadTexture("/res/Star3.png");
		 star4Tex = loadTexture("/res/Star4.png");
		 star5Tex = loadTexture("/res/Star5.png");
		
		 cowTex = loadTexture("res/moo2.png");
		 starBuffTex = loadTexture("res/StarBuff.png");
		 cookieTex = loadTexture("res/Cookie.png");
		 plusTex = loadTexture("res/plus.png");
		 asteroidTex = loadTexture("res/asteroid.png");
		 magnet = (loadTexture("res/magnet.png"));
	}
	//Loads a image based on the Path and puts it on a texture.
	private Texture loadTexture(String path){
		Texture tex = null;
		try {
			tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO: handle exception
		}
		return tex;
	}
	//Draw a texture on the screen based on the x and y values it takes in.
	public void drawTexture(Texture tex, float x, float y){
		tex.bind();
		glBegin(GL_QUADS);
		
			glTexCoord2f(0, 1);
			glVertex2f(x, y);
			
			glTexCoord2f(1, 1);
			glVertex2f(x+tex.getTextureWidth(), y);
			
			glTexCoord2f(1, 0);
			glVertex2f(x+tex.getTextureWidth(), y+tex.getTextureHeight());
			
			glTexCoord2f(0, 0);
			glVertex2f(x, y+tex.getTextureHeight());
			
			glEnd();
			
	}
	//rotates and then draws a texture to the screen.
	public void drawRotatingTexture(Texture tex, float x, float y, float rotation){
		float xCenter = tex.getTextureWidth()/2;
		float yCenter = tex.getTextureHeight()/2;
		tex.bind();
		glPushMatrix();
		glTranslatef(x+xCenter,y+yCenter, 0);
		glRotatef(rotation, 0f, 0f, 1f);
		glTranslatef(-x-xCenter,-y-yCenter, 0);
		glBegin(GL_QUADS);
		
			glTexCoord2f(0, 1);
			glVertex2f(x, y);
			
			glTexCoord2f(1, 1);
			glVertex2f(x+tex.getTextureWidth(), y);
			
			glTexCoord2f(1, 0);
			glVertex2f(x+tex.getTextureWidth(), y+tex.getTextureHeight());
			
			glTexCoord2f(0, 0);
			glVertex2f(x, y+tex.getTextureHeight());
			
			glEnd();
			glPopMatrix();
			
	}

	public Texture getStar1File() {
		return star1Tex;
	}

	public Texture getStar2File() {
		return star2Tex;
	}

	public Texture getStar3File() {
		return star3Tex;
	}

	public Texture getStar4File() {
		return star4Tex;
	}

	public Texture getStar5File() {
		return star5Tex;
	}

	public Texture getCowTex() {
		return cowTex;
	}

	public Texture getStarBuffTex() {
		return starBuffTex;
	}

	public Texture getCookieTex() {
		return cookieTex;
	}

	public Texture getNumber1tex() {
		return number1tex;
	}

	public Texture getNumber2tex() {
		return number2tex;
	}

	public Texture getNumber3tex() {
		return number3tex;
	}

	public Texture getPlusTex() {
		return plusTex;
	}

	public Texture getAsteroidTex() {
		return asteroidTex;
	}

	public Texture getMagnet() {
		return magnet;
	}
}
