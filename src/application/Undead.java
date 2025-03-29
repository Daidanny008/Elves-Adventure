package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Undead extends Skins {
	
	// Declare class by input
	Undead(int dir, int height) {

		// Initialize archer image by direction
		if (dir == EAST) {
			imgArcher = new Image("file:images/undead archerR.png");
		}
		else if (dir == WEST) {
			imgArcher = new Image("file:images/undead archerL.png");
		}

		// Initialize archer ImageView
		ivArcher = new ImageView(imgArcher);

		// Resize ImageView
		ivArcher.setPreserveRatio(true);
		ivArcher.setFitHeight(height);

		// Initialize back-story
		name = "Name:\nEdwards Katz";
		race = "Race:\nUndead";
		backstory = "Once a prince of a nearby kingdom who died in the previous wars of robot invasions. His rest "
				+ "is bothered even after death. So he climbed out of his grave and began his revenge on robots."
				+ " It begins again....";
	}

	// Return archer image
	public ImageView getArcher() {
		return ivArcher;
	}

	// Return archer name
	public String getName() {
		return name;
	}

	// Return archer race
	public String getRace() {
		return race;
	}

	// Return archer story
	public String getStory() {
		return backstory;
	}


}
