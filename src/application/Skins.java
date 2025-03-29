package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Skins {

	//---------------------------------------------------Globals------------------------------------------------------
	// Data variables: direction, images, position, speed
	Image imgBack, imgBoard, imgArcher;
	ImageView ivBack, ivBoard, ivArcher;
	String name, race, backstory;
	int dir, height, x, y, speed;
	final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;

	//---------------------------------------------------Constructor------------------------------------------------------
	public Skins() {

		// Initialize background image
		imgBack = new Image("file:images/skins back.png");
		ivBack = new ImageView(imgBack);

		// Initialize text board image
		imgBoard = new Image("file:images/text board.png");
		ivBoard = new ImageView(imgBoard);
		ivBoard.setX(888);
		ivBoard.setY(173);

		// Initialize speed, x, y
		speed = 5;
		x = 0;
		y = 0;

	}

	//---------------------------------------------------Methods------------------------------------------------------

	// Get Methods:

	// Get background image
	public ImageView getBack() {
		return ivBack;
	}

	// Get text board image
	public ImageView getBoard() {
		return ivBoard;
	}

	// Get xPos
	public int getX() {
		return x;
	}

	// Get yPos
	public int getY() {
		return y;
	}
	
	// Get height
	public int getHeight() {
		return height;
	}

	// Set Methods:

	// Set height
	public void setHeight(int height) {
		this.height = height;
		ivArcher.setFitHeight(height);
	}

	// Set xPos
	public void setX(int xPos) {
		x = xPos;
		ivArcher.setX(x);
	}

	// Set yPos
	public void setY(int yPos) {
		y = yPos;
		ivArcher.setY(y);
	}

	// Other Methods:

	// Move character
	public void move(int dir) {
		
		if (dir == NORTH) {
			y -= 5;
		}
		else if (dir == SOUTH) {
			y += 5;
		}
		else if (dir == EAST) {
			x += 5;
		}
		else if (dir == WEST) {
			x -= 5;
		}

		ivArcher.setX(x);
		ivArcher.setY(y);

	}

	// Abstract methods:
	public abstract ImageView getArcher();
	public abstract String getName();
	public abstract String getRace();
	public abstract String getStory();

}
