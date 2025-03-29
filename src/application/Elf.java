package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Elf extends Skins {
	
	// Declare class by input
	Elf(int dir, int height) {
		
		// Initialize archer image by direction
		if (dir == EAST) {
			imgArcher = new Image("file:images/elf1R.png");
		}
		else if (dir == WEST) {
			imgArcher = new Image("file:images/elf1L.png");
		}
		
		// Initialize archer ImageView
		ivArcher = new ImageView(imgArcher);
		
		// Resize ImageView
		ivArcher.setPreserveRatio(true);
		ivArcher.setFitHeight(height);
		
		// Initialize back-story
		name = "Name:\nAlexander Xistrith";
		race = "Race:\nElf";
		backstory = "As a member of the nature elves, Alex is outstanding in archery. He's fighting the robot force"
				+ " due to the damages they have caused to the forest. Their mother tree, Yggdrasil, has ordered"
				+ " an all-out war against the robots in the name of elves.";
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
