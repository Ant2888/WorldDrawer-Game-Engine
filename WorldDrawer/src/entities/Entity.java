package entities;

import javafx.scene.canvas.GraphicsContext;
import mapping.Sprite;
import maths.Coordinate;

public abstract class Entity {
	
	//most of these are arrays because you should NOT be changing the sizes
	protected volatile double internalCounter = 0;
	protected Sprite[] sprite;
	protected double[] speed;
	protected boolean[] reverse;
	protected Coordinate coords;
	
	protected int currentAnimation = 0;
	
	public Entity(Sprite[] sprite, double[] animSpeed, Coordinate coords){
		this.sprite = sprite;
		this.speed = animSpeed;
		this.coords = coords;
		reverse = new boolean[sprite.length];
	}
	
	public Entity(Sprite[] sprite, Coordinate coords){
		this.sprite = sprite;
		this.speed = new double[]{1};
		this.coords = coords;
		reverse = new boolean[sprite.length];
	}
	
	public Entity(Sprite[] sprite) {
		this.sprite = sprite;
		this.speed = new double[]{1};
		coords = new Coordinate(0, 0);
		reverse = new boolean[sprite.length];
	}
	
	//feel free to override but use the conditional as apart of the custom anim
	//but override it seperately and throw it into the drawable
	protected void drawEntity(GraphicsContext g, double x, double y){
		if(internalCounter > speed[currentAnimation]*5){ //the times 5 is just a futz value to poorly normalize the speed
			g.drawImage(sprite[currentAnimation].getCurAnim(), x, y);
			internalCounter = 0;
		}else{
			//this is to continually draw the image while not animating
			g.drawImage(sprite[currentAnimation].getAnimStatic(), x, y);
		}
	}
	
	//DO NOT OVERRIDE THIS!!!! Just super it and do whichever you want
	public void getUpdates(double delta){
		internalCounter += delta;
	}
	
	public void setAnimSpeed(double speed){
		this.speed[currentAnimation] = speed;
	}
	
	public void setAnimationState(int stateToSet){
		this.currentAnimation = stateToSet;
	}
	
	public double getAnimSpeed(){
		return speed[currentAnimation];
	}
	
	public Sprite getCurAnimSprite(){
		return sprite[currentAnimation];
	}
}
