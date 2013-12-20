package spacecow.engine;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class AnimationHandler {

	private Animation starAnimation;
	private Image starIma=null;;
	
	public AnimationHandler(){
		try {
			starIma= new Image("res/StarBuff.png");
			Image[] starImArr = (Image[]) rotateIm(starIma).toArray().clone();
			System.out.println("ArraySize"+starImArr.length);
			int[] starAniDur = {100,100,100,100,100,100,100,100};
			System.out.println("intZize"+starAniDur.length);
			starAnimation=new Animation(starImArr,starAniDur,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Animation getStar() {
		return starAnimation;
	}
	
	private static ArrayList<Image> rotateIm(Image image){
		Image im = image;
		ArrayList<Image> rot = new ArrayList<>();
		System.out.println("hej");
		float rotation = 45;
		for (int i = 0; i < 8; i++) {
			image.rotate(rotation);
			rot.add(im);
			rotation += 45;
			System.out.println("da");
		}
		return rot;
	}
}
