package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Arrow {

	// Variables
	private Image imgEast, imgWest;
	private ImageView ivArrow;
	public boolean fired;
	private double xPos, yPos, width, height;
	private int dir;
	final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;

	// Constructors
	public Arrow() {

		// Initialize Images
		imgEast = new Image("file:images/arrowR.png");
		imgWest = new Image("file:images/arrowL.png");

		// Initialize ImageView
		ivArrow = new ImageView(imgEast);

		// Initialize direction
		dir = EAST;

		// Initialize fired boolean
		fired = false;

		// Initialize position and dimensions
		xPos = -200;
		yPos = -200;
		width = imgEast.getWidth();
		height = imgEast.getHeight();

		// Set position
		ivArrow.setX(xPos);
		ivArrow.setY(yPos);

	}

	// Constructor with position
	public Arrow(double x, double y) {

		// Initialize Images
		imgEast = new Image("file:images/arrowR.png");
		imgWest = new Image("file:images/arrowL.png");

		// Initialize ImageView
		ivArrow = new ImageView(imgEast);

		// Initialize direction
		dir = EAST;

		// Initialize fired boolean
		fired = false;

		// Initialize position and dimensions
		xPos = x;
		yPos = y;
		width = imgEast.getWidth();
		height = imgEast.getHeight();

		// Set position
		ivArrow.setX(xPos);
		ivArrow.setY(yPos);

	}

	// Methods
	// Get direction
	public int getDirection() {
		return dir;
	}

	// Get height
	public double getHeight() {
		return height;
	}

	// Get width
	public double getWidth() {
		return width;
	}

	// Get xPos
	public double getX() {
		return xPos;
	}

	// Get yPos
	public double getY() {
		return yPos;
	}

	// Get fired boolean
	public boolean isFired() {
		return fired;
	}

	// Move arrow, speed 20
	public void move() {

		// Move by direction
		if (dir == EAST) {
			xPos += 20;
		}
		else if (dir == WEST) {
			xPos -= 20;
		}

		// Update Image position
		ivArrow.setX(xPos);
	}

	// Initialize arrow 
	public void setPosition(double playerX, double playerY, int dir) {

		// Set direction
		this.dir = dir;

		// Adjust by direction
		if (this.dir == EAST) {
			xPos = playerX + 75;
		}
		else {
			xPos = playerX;
		}

		// Adjust height
		yPos = playerY + 40;

		// Set fired to true
		fired = true;

		// Update position
		ivArrow.setX(xPos);
		ivArrow.setY(yPos);

	}

	// Set xPos
	public void setX(int x) {
		xPos = x;
	}

	// Set yPos
	public void setY(int y) {
		yPos = y;
	}

	// Stop arrow, move off screen
	public void stopArrow() {
		xPos = -200;
		yPos = -200;
		ivArrow.setX(xPos);
		ivArrow.setY(yPos);
	}

	// Check if in screen
	public boolean isOffScreen(double edge) {

		// Initialize as false
		boolean offScreen = false;

		// If out of bounds
		if (xPos >= edge || xPos + width < 0) {
			offScreen = true;
			fired = false;
		}
		else {
			offScreen = false;
		}

		// Return if off screen
		return offScreen;

	}

	// Get image
	public ImageView getNode() {

		// Get by direction
		if (dir == EAST) {
			ivArrow.setImage(imgEast);
		}
		else if (dir == WEST) {
			ivArrow.setImage(imgWest);
		}

		// Return image
		return ivArrow;

	}

}
