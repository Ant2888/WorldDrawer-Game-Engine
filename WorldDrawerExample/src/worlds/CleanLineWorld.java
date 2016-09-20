package worlds;

import displays.WorldManager;
import displays.WorldTemplate;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class CleanLineWorld extends WorldTemplate{

	private Path path;
	private EventHandler<MouseEvent> events;

	public CleanLineWorld(WorldManager wm) {
		super(wm, null);
	}

	private volatile double delay = 0;
	@Override
	public void update(double tickDelta) {
		if(delay>=100){
			boolean hasCtrl = false, hasZ = false;
			for(KeyCode e: wm.updateKeys()){
				if(e == KeyCode.CONTROL) hasCtrl = true;
				if(e == KeyCode.Z) hasZ = true;
				if(e == KeyCode.ENTER){
					wm.setLevel(DottedLineWorld.class);
					delay = 0;
				}
			}
			if(hasCtrl && hasZ){
				path.getElements().clear();
				delay = 0;
			}
		}
		delay+=tickDelta;
	}


	@Override
	public void draw(int prevFrames, GraphicsContext g) {
	}

	@Override
	public void prepForDelete() {
		//Need to unregister all handlers and nodes added
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				wm.getGame().removeFromRoot(path);
				wm.getCanvas().removeEventHandler(MouseEvent.ANY, events);
			}
		});
	}

	private void addMouse(){
		events = new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(event.getEventType() == MouseEvent.MOUSE_PRESSED){
					path.getElements().add(new MoveTo(event.getX(), event.getY()));
				}else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED){
					path.getElements().add(new LineTo(event.getX(), event.getY()));
				}
			}
		};
		wm.getCanvas().setOnMousePressed(events);
		wm.getCanvas().setOnMouseDragged(events);
	}

	@Override
	public void allocateResources() {
		path = new Path();
		path.setStrokeWidth(2);
		path.setStroke(Color.BLACK);
		wm.getGame().addToRoot(path); //MAKE SURE THINGS LIKE THIS ARE REMOVED
		addMouse();
	}

}
