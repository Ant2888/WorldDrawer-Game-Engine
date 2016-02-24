package mapping;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteLoader {
	//Note the sprite map MUST be some X by Y (y can be x) with squares of size 100px (now a variable still must be sq)!!
	private int pxRowStart;
	private int rowNum;
	private Sprite generateSprite;
	private int cols;
	
	//defaults 100
	public SpriteLoader(String sheet, int row, int amountOfCols) {
		pxRowStart = 1+(row*100);//count from 0
		this.cols = amountOfCols;
		this.rowNum = row;
		generateSprite(sheet);
	}
	
	public SpriteLoader(String sheet, int row, int amountOfCols, int rowSize) {
		pxRowStart = 1+(row*rowSize);//count from 0
		this.cols = amountOfCols;
		this.rowNum = row;
		generateSprite(sheet);
	}

	//should cache the sheet here for future uses
	private void generateSprite(String path, int rowSize) {
		Image imgOfSheet = new Image(path); 
		PixelReader pxRead = imgOfSheet.getPixelReader();//cache this then just loop 
		//sure we don't already have it through cache to make
		Image[] anims = new Image[cols];
		
		int pxColStart = 0;
		for (int i = 0; i < anims.length; i++) {
			anims[i] = new WritableImage(pxRead, pxColStart, pxRowStart, rowSize, rowSize);
			pxColStart += (rowSize+1);
		}
		generateSprite = new Sprite(anims, path, rowNum);
	}
	
	private void generateSprite(String path){
		generateSprite(path, 100);
	}
	
	public Sprite getSprite(){
		return generateSprite;
	}
	
}
