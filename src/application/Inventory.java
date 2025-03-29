package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Inventory {

	//---------------------------------------------------Globals------------------------------------------------------
	// Images
	Image imgBoard, imgBanner, imgStorage, imgCancel;
	ImageView ivBoard, ivBanner, ivStorage, ivCancel;
	
	// Storage array
	int[][] storage;
	
	//---------------------------------------------------Constructor------------------------------------------------------
	public Inventory() {
		
		// Initialize images
		imgBoard = new Image("file:images/banner wood.png");
		imgBanner = new Image("file:images/subbanner.png");
		imgStorage = new Image("file:images/storage.png");
		imgCancel = new Image("file:images/cancel.png");

		// Initialize ImageViews
		ivBoard = new ImageView(imgBoard);
		ivBanner = new ImageView(imgBanner);
		ivStorage = new ImageView(imgStorage);
		ivCancel = new ImageView(imgCancel);
		
		// Initialize storage array
		storage = new int[3][12];
		storage[0][0] = 1; // Beginning weapon
		
		// TESTING: start with 200 silver: storage[1][0] = 200;
		
	}

	//---------------------------------------------------Methods------------------------------------------------------

	// Get Methods:
	
	// Get main banner
	public ImageView getBanner() {
		return ivBoard;
	}
	
	// Get sub banner
	public ImageView getSubBanner() {
		return ivBanner;
	}
	
	// Get storage background
	public ImageView getBackground() {
		return ivStorage;
	}
	
	// Get x mark image
	public ImageView getCancel() {
		return ivCancel;
	}
	
	// Get storage
	public int getStored(int index, int index2) {
		return storage[index][index2];
	}
	
	// Set storage
	public void setStored(int index, int index2, int change) {
		storage[index][index2] = change;
	}
	
	// Change storage
	public void ChangeStored(int index, int index2, int change) {
		storage[index][index2] += change;
	}
	
}
