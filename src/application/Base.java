/*
 * Data class for Home Base
 */

package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Base {

	//---------------------------------------------------Globals------------------------------------------------------
	// Images - castle and background
	Image door, room, top, home, homeU, homeD;

	// ImageView - castle and background
	ImageView ivDoor, ivRoom, ivTop, ivHome, ivHomeU, ivHomeD, ivHomeU2, ivHomeD2;
	ArrayList <ImageView> castle, stars;

	// Total width and height, current y position of the screen, number of floors
	int width, height, yPos, floorNum;
	final int NORTH = 0, SOUTH = 1;
	ArrayList <Integer> castleYPos, starsYPos;

	//---------------------------------------------------Constructor------------------------------------------------------
	public Base() {

		// Images - castle and background
		door = new Image("file:images/door.png");
		room = new Image("file:images/room.png");
		top = new Image("file:images/top.png");

		home = new Image("file:images/home back.jpeg");
		homeU = new Image("file:images/home backU.jpeg");
		homeD = new Image("file:images/home backD.jpeg");

		// ImageView - castle and background
		ivDoor = new ImageView(door);
		ivRoom = new ImageView(room);
		ivTop = new ImageView(top);

		ivHome = new ImageView(home);
		ivHomeU = new ImageView(homeU);
		ivHomeD = new ImageView(homeD);
		ivHomeU2 = new ImageView(homeU);
		ivHomeD2 = new ImageView(homeD);

		// Initialize number of star backgrounds and floor number of castle
		floorNum = 3;

		// Initialize width, height, and yPos
		width = 612;
		height = 408 + (floorNum + 1) * 200;
		yPos = 0;

		// Declare ImageView ArrayLists
		castle = new ArrayList<ImageView>();
		stars = new ArrayList<ImageView>();

		// Initialize ImageView ArrayLists
		Collections.addAll(castle, ivDoor, ivRoom, ivTop);
		Collections.addAll(stars, ivHome, ivHomeD, ivHomeU, ivHomeD2, ivHomeU2);

		// Declare Integer ArrayLists
		castleYPos = new ArrayList<Integer>();
		starsYPos = new ArrayList<Integer>();

		// Initialize Integer ArrayLists
		Collections.addAll(castleYPos, (int)(645 - ivDoor.getImage().getHeight() + yPos), 
				(int)(645 - ivDoor.getImage().getHeight() - 200 + yPos),
				(int)(645 - ivDoor.getImage().getHeight() - 200 - ivTop.getImage().getHeight() + yPos));

		for (int i = 0; i < 5; i++) {
			starsYPos.add((int)(675 - ivHome.getImage().getHeight() - i * 200 + yPos));
		}

	}

	//---------------------------------------------------Methods------------------------------------------------------

	// Get Methods:

	// Get ImageView from castle
	public ImageView getIvC(int index) {
		return castle.get(index);
	}

	// Get ImageView from stars
	public ImageView getIvS(int index) {
		return stars.get(index);
	}

	// Get Castle door
	public ImageView getCastleDoor() {
		return ivDoor;
	}

	// Get Castle floor
	public ImageView getCastleFloor() {
		return ivRoom;
	}

	// Get Castle top
	public ImageView getCastleTop() {
		return ivTop;
	}

	// Get Background initial
	public ImageView getStarsInitial() {
		return ivHome;
	}

	// Get Background Up
	public ImageView getStarsUp() {
		return ivHomeU;
	}

	// Get Background Down
	public ImageView getStarsDown() {
		return ivHomeD;
	}

	// Get yPos from castle
	public int getCastleYPos(int index) {
		return castleYPos.get(index);
	}

	// Get yPos from stars
	public int getStarsYPos(int index) {
		return starsYPos.get(index);
	}

	// Get width
	public int getWidth() {
		return width;
	}

	// Get Height
	public int getHeight() {
		return height;
	}

	// Get yPos
	public int getYPos() {
		return yPos;
	}

	// Get floors number
	public int getFloorNum() {
		return floorNum;
	}

	// Calculate yPos of each image
	public void calcYPos() {

		// Initialize arrays
		castleYPos.clear();
		starsYPos.clear();

		// Calculate first
		castleYPos.add(((int)(645 - ivDoor.getImage().getHeight() + yPos)));
		starsYPos.add((int)(675 - ivHome.getImage().getHeight() + yPos));

		// Calculate middle 
		for (int i = 1; i < floorNum - 1; i++) {
			castleYPos.add((int)(645 - ivDoor.getImage().getHeight() - 200 * (floorNum - 2) + yPos));
			starsYPos.add((int)(675 - ivHome.getImage().getHeight() - 200 * (floorNum - 2) + yPos));
		}

		// Calculate top of castle
		castleYPos.add((int)(645 - ivDoor.getImage().getHeight() - 200 * (floorNum - 2) - ivTop.getImage().getHeight() + yPos));

		// Calculate top of stars
		for (int i = floorNum - 1; i < floorNum + 2; i++) {
			starsYPos.add((int)(675 - ivHome.getImage().getHeight() - 200 * (i) + yPos));
		}

	}

	// Background movement
	public void setYPos(int dir) {

		// Change by direction, speed = 5, check bounds
		if (dir == NORTH) {
			yPos += 5;
			if (yPos + 700 >= height) {
				yPos -=5;
			}
		}
		else if (dir == SOUTH) {
			yPos -= 5;
			if (yPos <= 0) {
				yPos = 0;
			}
		}
	}


}
