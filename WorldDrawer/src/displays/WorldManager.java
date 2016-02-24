package displays;

import java.util.List;
import java.util.PriorityQueue;

import controls.Game;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class WorldManager extends PriorityQueue<WorldTemplate>{
	private static final long serialVersionUID = 1L;
	
	//The structure for adding levels:
	//Add a levels to the LEVELS enum
	//Give it an arbitrary int
	//place it in the set level switch following the already placed examples
	//Give the level an appropriate floatgrid 
	//then in the menu/prev level simply call its WM's setLevel method and profit (hence LEVELS is public)
	
	private GraphicsContext g;
	private Game game;
	private Canvas canvas;
	
	public enum LEVELS{
		CLEAN_LINE(0), DOTTED_LINE(1);
		
		private int num;
		
		LEVELS(int num){
			this.num = num;
		}
		
		public int getNumeric(){
			return num;
		}
	}
	
	public WorldManager(GraphicsContext g, Game game, Canvas canvas) {
		this.g = g;
		this.game = game;
		this.canvas = canvas;
		setLevel(LEVELS.CLEAN_LINE);
	}
	
	public void update(double delta){
		this.peek().update(delta);
	}
	
	public void draw(int frames, GraphicsContext g){
		this.peek().draw(frames, g);
	}
	
	public void draw(int frames){
		draw(frames, g);
	}
	
	public void draw(){
		draw(0,g);
	}

	public void setLevel(LEVELS level){
		switch (level.getNumeric()) {
		case 0:
			if(!isEmpty()) pollWorld();
			this.add(new CleanLineWorld(this));
			break;
		case 1:
			if(!isEmpty()) pollWorld();
			this.add(new DottedLineWorld(this));
			break;
		default: //default to going to the main screen
			break;
		}
	}
	
	public WorldTemplate pollWorld(){
		this.peek().prepForDelete();
		return this.poll();
	}
	
	public synchronized List<KeyCode> updateKeys(){
		return game.getKeys();
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	//eh probably don't want to do this
	public Game getGame(){
		return game;
	}
}
