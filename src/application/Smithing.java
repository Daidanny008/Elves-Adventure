package application;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Smithing {

	//---------------------------------------------------Globals------------------------------------------------------
	// Images
	ArrayList <ImageView> bows;

	//---------------------------------------------------Constructor------------------------------------------------------
	public Smithing() {

		// Initialize ArrayList
		bows = new ArrayList<ImageView>();

		// Add Images to ArrayList
		for (int i = 0; i < 9; i++) {
			bows.add(new ImageView(new Image("file:Bow sprite/bows" + (i+1) + ".png")));
		}

	}

	//---------------------------------------------------Methods------------------------------------------------------

	// Get ImageView
	public ImageView getBow(int index) {
		return bows.get(index);
	}


}
