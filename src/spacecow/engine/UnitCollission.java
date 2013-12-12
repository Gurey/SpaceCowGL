package spacecow.engine;

import org.newdawn.slick.geom.Rectangle;

public class UnitCollission {
	
	public static boolean isColliding(Rectangle rect1, Rectangle rect2){
		return (rect1.intersects(rect2));
	}

}
