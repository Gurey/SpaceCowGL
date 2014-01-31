package spacecow.objects;

import org.newdawn.slick.opengl.Texture;

import spacecow.buffs.Magnet;
import spacecow.engine.GameObject;
import spacecow.engine.Score;

public class MagnetObj extends GameObject {

	private Magnet magnet;
	
	public MagnetObj(Texture tex, Score score, Player player, Magnet magnet) {
		super(tex, score, player);
		this.magnet = magnet;
		setMagnetic(false);
		setRotating(true);
		setRotationSpeed((float) (-2+Math.random()*4));
	}
	//Init the MagnetBuff if its available, else increase the time of the existing Magnet buff with 3 seconds.
	@Override
	public void collisionAction(){
		if (magnet.isAvailable()) {
			magnet.initMagnet();
		}	
		else magnet.incTime(3000);
		score.setMagnetCol(score.getMagnetCol()+1);
	
	}

}
