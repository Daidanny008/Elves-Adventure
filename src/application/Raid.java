package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Raid {

	//---------------------------------------------------Globals------------------------------------------------------
	// Images
	Image imgBack, imgHome, imgHappy, imgRed, imgArtillery;
	ImageView ivBack, ivHome, ivHappy, ivRed, ivArtillery;

	//---------------------------------------------------Constructor------------------------------------------------------
	public Raid() {

		// Initialize images
		imgBack = new Image("file:images/raid.png");
		imgHome = new Image("file:images/home.png");
		imgHappy = new Image("file:images/happy robotL.png");
		imgRed = new Image("file:images/red robot.png");
		imgArtillery = new Image("file:images/artillery robot.png");

		// Initialize ImageViews
		ivBack = new ImageView(imgBack);
		ivHome = new ImageView(imgHome);
		ivHappy = new ImageView(imgHappy);
		ivRed = new ImageView(imgRed);
		ivArtillery = new ImageView(imgArtillery);

	}

	//---------------------------------------------------Methods------------------------------------------------------

	// Get Methods:

	// Get main banner
	public ImageView getBack() {
		return ivBack;
	}
	
	// Get home button
	public ImageView getHome() {
		return ivHome;
	}
	
	// Get happy robot
	public ImageView getHappy() {
		return ivHappy;
	}
	
	// Get red robot
	public ImageView getRed() {
		return ivRed;
	}
	
	// Get artillery robot
	public ImageView getArtillery() {
		return ivArtillery;
	}

}
