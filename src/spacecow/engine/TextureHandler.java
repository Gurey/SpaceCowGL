package spacecow.engine;

import java.io.IOException;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public final class TextureHandler{
	
	private static TextureHandler instance = null;
	
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
	
	public static TextureHandler getInstance(){
		if (instance == null) {
			instance = new TextureHandler();
		}
		return instance;
	}
	private TextureHandler() {
		
		 number1tex = TextureHandler.loadTexture("res/number1.png");
		 number2tex = TextureHandler.loadTexture("res/number2.png");
		 number3tex = TextureHandler.loadTexture("res/number3.png");
		
		 star1Tex = TextureHandler.loadTexture("/res/Star1.png");
		 star2Tex = TextureHandler.loadTexture("/res/Star2.png");
		 star3Tex = TextureHandler.loadTexture("/res/Star3.png");
		 star4Tex = TextureHandler.loadTexture("/res/Star4.png");
		 star5Tex = TextureHandler.loadTexture("/res/Star5.png");
		
		 cowTex = TextureHandler.loadTexture("res/cow.png");
		 starBuffTex = TextureHandler.loadTexture("res/StarBuff.png");
		 cookieTex = TextureHandler.loadTexture("res/Cookie.png");
		 plusTex = TextureHandler.loadTexture("res/plus.png");
	}

	private static Texture loadTexture(String path){
		
		Texture tex = null;
		try {
			tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
			System.out.println("texture id: "+tex.getTextureID());
			TextureImpl.unbind();
		} catch (IOException e) {
			// TODO: handle exception
		}
		return tex;
	}
	
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
	
	public static void drawQuad(float x, float y, Rectangle rect){
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		
		glTexCoord2f(1, 0);
		glVertex2f(x+rect.getWidth(), y);
		
		glTexCoord2f(0, 1);
		glVertex2f(x, y+rect.getHeight());
		
		glTexCoord2f(1, 1);
		glVertex2f(x+rect.getWidth(), y+rect.getHeight());
		
		glEnd();
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
}
