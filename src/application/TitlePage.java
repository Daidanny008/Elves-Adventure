package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class TitlePage {

	//---------------------------------------------------Globals------------------------------------------------------
	// Images: title page, board, banner
	Image imgTitle, imgBoard, imgBanner;
	ImageView ivTitle, ivBoard, ivBanner;
	
	// Integer data variables
	int width, height;

	// Fonts
	Font shan;
	
	//---------------------------------------------------Constructor------------------------------------------------------
	public TitlePage() {
		
		// Initialize title page image
		imgTitle = new Image("file:images/Title Page.png");
		ivTitle = new ImageView(imgTitle);
		
		// Initialize title page image width and height
		width = (int)imgTitle.getWidth();
		height = (int)imgTitle.getHeight();
		
		// Initialize board image
		imgBoard = new Image("file:images/board.png");
		ivBoard = new ImageView(imgBoard);
		ivBoard.setX(1375 / 2 - ivBoard.getImage().getWidth() / 2 + 25);
		ivBoard.setY(400);
		ivBoard.setPreserveRatio(true);
		ivBoard.setFitHeight(350);
		
		// Initialize banner image
		imgBanner = new Image("file:images/banner.png");
		ivBanner = new ImageView(imgBanner);
		ivBanner.setX(1375 / 2 - ivBanner.getImage().getWidth() / 2 - 90);
		ivBanner.setY(55);
		ivBanner.setPreserveRatio(true);
		ivBanner.setFitWidth(750);
	
	}
	
	//---------------------------------------------------Methods------------------------------------------------------

	// Get Methods:
	
	// Return title ImageView
	public ImageView getTitle() {
		return ivTitle;
	}
	
	// Return board ImageView
	public ImageView getBoard() {
		return ivBoard;
	}
	
	// Return banner ImageView
	public ImageView getBanner() {
		return ivBanner;
	}
	
	// Return title width
	public int getWidth() {
		return width;
	}
	
	// Return title height
	public int getHeight() {
		return height;
	}
}
