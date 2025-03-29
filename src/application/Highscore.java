package application;

// Imports
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Highscore {

	//---------------------------------------------------Globals------------------------------------------------------
	// Declare data variables and images
	ImageView ivBoard;
	int points;
	String name;

	//---------------------------------------------------Constructor------------------------------------------------------
	public Highscore() {

		// Initialize background Image
		ivBoard = new ImageView(new Image("file:images/leaderboard.png"));
		
		// Initialize points counter
		points = 0;
		
		// Initialize name
		name = "Somebody";

	}

	//---------------------------------------------------Methods------------------------------------------------------

	// Get Methods:
	
	// Return background image
	public ImageView getBack() {
		return ivBoard;
	}
	
	// Return points
	public int getPoints() {
		return points;
	}
	
	// Return name
	public String getName() {
		return name;
	}
	
	// Set name
	public void setName(String name) {
		this.name = name;
	}
	
	// Add points
	public void addPoints(int points) {
		this.points += points;
	}

}


