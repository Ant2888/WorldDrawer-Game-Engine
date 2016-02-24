package mapping;

import javafx.scene.image.Image;

public class Sprite {
	//Note the sprite's can only be place on unique row and animation cycles the the respective cols.
	private String sheet;
	private Image[] sprite;
	private int row;
	private int curAnim = 0;
	private boolean hasReverse = false;
	private int lastDrawn = 0;
	
	public Sprite(Image[] sprite, String sheet, int row) {
		this.sprite = sprite;
		this.row = row;
		this.sheet = sheet;
	}public Sprite(Image[] sprite, String sheet, int row, boolean reverse) {
		this.sprite = sprite;
		this.row = row;
		this.sheet = sheet;
		reverse = hasReverse;
	}
	
	public void addReverseAnim(boolean reverse){
		hasReverse = reverse;
	}
	
	//defaults 0
	public void setAnimState(int state){
		this.curAnim = state;
		if(curAnim > sprite.length){
			curAnim%=sprite.length;
		}
	}
	
	//use for showing the image and not animating
	public Image getAnimStatic(){
		return sprite[lastDrawn];
	}
	
	//allowing for reversing the sprite so you do not need to waste space/time
	private boolean amReversing = false;
	public Image getCurAnim(){
		if(sprite.length == 1) return sprite[0]; //incase it's a static image
		if(amReversing && curAnim > -1){
			lastDrawn = curAnim;
			return sprite[curAnim--];
		}else if(hasReverse && curAnim>=sprite.length){
			amReversing = true;
			lastDrawn = curAnim-1;
			return sprite[--curAnim];
		}else{
			if(curAnim>=sprite.length){
				curAnim = 0;
			}else if(curAnim < 0){
				if(hasReverse || amReversing) amReversing = false;
				curAnim = 0;
			}
			lastDrawn = curAnim;
			return sprite[curAnim++];
		}
	}
	
	public int getRowLocation(){
		return row;
	}
	
	public String getSheetPath(){
		return sheet;
	}
	
	public int getAmountOfCols(){
		return sprite.length;
	}
	
	public Image DEBUG_FIRST_ELEM(){
		return sprite[0];
	}
}
