package maths;

public class Coordinate {
	public double x,y;
	
	public Coordinate(double x, double y) {
	 this.x = x;
	 this.y = y;
	}
	
	public float getAbsoluteMagnitude(Coordinate coord){
		return (float) Math.abs(Math.sqrt((coord.x*coord.x)+(coord.y*coord.y)));
	}
	
	public float getAbsoluteMagnitude(double x, double y){
		return getAbsoluteMagnitude(new Coordinate(x, y));
	}
}
