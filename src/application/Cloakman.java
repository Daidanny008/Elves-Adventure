package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cloakman extends Skins {

	// Declare class by input
	Cloakman(int dir, int height) {

		// Initialize archer image by direction
		if (dir == EAST) {
			imgArcher = new Image("file:images/cloak archerR.png");
		}
		else if (dir == WEST) {
			imgArcher = new Image("file:images/cloak archerL.png");
		}

		// Initialize archer ImageView
		ivArcher = new ImageView(imgArcher);

		// Resize ImageView
		ivArcher.setPreserveRatio(true);
		ivArcher.setFitHeight(height);

		// Initialize back-story
		name = "Name:\nRobin Hood";
		race = "Race:\nHuman";
		backstory = "Some say he's an excellent swordman and archer from England, possibly of noble descent. It is "
				+ "not known what his motives are to help fight robots in the elves forest. But time will reveal "
				+ "many secrets, perhaps this as well....";
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
