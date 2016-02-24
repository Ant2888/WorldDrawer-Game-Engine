package entities;

import javafx.scene.canvas.GraphicsContext;
import mapping.Sprite;
import mapping.SpriteLoader;
import maths.Coordinate;

public class HeartObject extends Entity implements Drawable{

	public HeartObject(Coordinate coords) {
		super(new Sprite[]{new SpriteLoader("/SpriteSheet.png", 0, 4).getSprite()}, new double[]{1.0}, coords);
	}

	@Override
	public void draw(GraphicsContext g, double x, double y) {
		super.drawEntity(g, x, y);
	}

}
