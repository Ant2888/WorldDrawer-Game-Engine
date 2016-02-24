package rendering;

import java.util.HashMap;

import entities.Drawable;
import javafx.scene.canvas.GraphicsContext;
import maths.Coordinate;

public class FloatGrid {

	private int width;
	private int height;
	private HashMap<Coordinate, Object> drawables; //everything that CAN be on the map
	private HashMap<Coordinate, Object> areVisible; //everything that SHOULD BE visible 
	
	public FloatGrid(int width, int height) {
		this.width = width;
		this.height = height;
		drawables = new HashMap<>(width*height, 1);
		areVisible = new HashMap<>(width*height, 1);
	}
	
	public void addToGrid(Coordinate coords, Object o){
		if(coords.x > width || coords.y > height);
		else 
			drawables.put(coords, o);
	}
	
	public void updateRenderGrid(Coordinate camera){
		//render everything around the given coordinate as long as the coords are within the screen
		for(Coordinate chkToRender: drawables.keySet()){
			//added a futz of 10% just incase -- not height is .55f since it will be cut in half at rendering time (we will have negative y's)
			if(camera.getAbsoluteMagnitude(chkToRender) <= 1.1F*width && camera.getAbsoluteMagnitude(chkToRender) <= .55F*height
					&& drawables.get(chkToRender) instanceof Drawable){ //make sure we should even bother drawing it
				areVisible.put(chkToRender, drawables.get(chkToRender));
			}else{
				if(areVisible.get(chkToRender) != null) areVisible.remove(chkToRender);
			}
		}
	}
	
	public void renderGrid(GraphicsContext g){
		for(Coordinate render: areVisible.keySet()){
			//couldnt be in here if it wasn't a drawable
			Drawable toDraw = (Drawable) areVisible.get(render);
			toDraw.draw(g, render.x, render.y);
		}
	}
}
