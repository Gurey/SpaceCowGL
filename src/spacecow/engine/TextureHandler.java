package spacecow.engine;

import java.io.IOException;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public final class TextureHandler {
	
	private static TextureHandler instance = null;
	
	public static TextureHandler getInstance(){
		if (instance == null) {
			instance = new TextureHandler();
		}
		return instance;
	}
	

	private Texture star1Tex = TextureHandler.loadTexture("./res/Star1.png");
	private Texture star2Tex = TextureHandler.loadTexture("./res/Star2.png");
	private Texture star3Tex = TextureHandler.loadTexture("./res/Star3.png");
	private Texture star4Tex = TextureHandler.loadTexture("./res/Star4.png");
	private Texture star5Tex = TextureHandler.loadTexture("./res/Star5.png");
	
	private Texture cowTex = TextureHandler.loadTexture("res/cow.png");
	
	private Texture starBuffTex = TextureHandler.loadTexture("res/StarBuff.png");

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
	
	public static void drawTexture(Texture tex, float x, float y){
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
	
}
