/**
 * Name: Danny Dai
 * Date: January 20, 2023
 * Course Code: ICS4U1-01
 * Brief Description: 
 * 		- Entering the game, there would be a title screen and 3 buttons to choose. 
 * 		- Choosing help simply displays some tips and controls about playing the game. 
 * 		- Choosing start will start the game, along with some dialogue of the back stories of this game.
 * 		- Choosing leader-board would skip the tutorial dialogues (and naming), and exiting it will directly
 * 		lead to the home base.
 * 		- In the home base, there are several buildings. The player can switch his character through the 
 * 		'Skins' building. Fight robots through 'Raid', which drop materials that can be used to buy
 * 		better weapons at the 'Smith'. Check his own collection of weapons, materials, achievements in 
 * 		'Inventory'. And lastly, check the leader-board (score is counted when player quits the game).
 * Brief Details: 
 * 		- In order to make the amount of rooms in this game, Stage was defined as a global and resized
 * 		multiple times. Also, many sub Panes were created to pack all of the objects in one room together,
 * 		then, instead of adding and removing all the objects, only changing the Panes is necessary.
 * 		- Alerts were used in the game to give tips to the player and ask for information like their name.
 * 		- GridPane was used for the inventory room along with many HBox and VBox to perfectly align every item.
 * 		- Many 1D arrays and ArrayLists, as well as 2D array were used to store repetitive data like dozens
 * 		of similar objects
 * 		- Reading, writing, and sorting files was essential in creating the high score feature
 * 		- Polymorphism and inheritance were used to have different characters and similar set of values
 * 		- Animation and collision detection were used to check if arrow hit an enemy, and show character
 * 		or background movement.
 * 		- Sounds were used to create background musics and short audio
 * 		- Multiple classes were used to minimize declarations in main, and make code more readable
 * 		- The game includes many calculations for the exact placement of objects, and keeping objects in 
 * 		bound
 * 		- Integer variables and animation were used to create health bars for the robots
 */

package application;

// Imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main extends Application {

	//---------------------------------------------------Globals------------------------------------------------------

	// Room access booleans
	boolean titlePage, homebase, inventoryRoom, skinRoom, raidRoom, smithRoom, highscoreRoom;

	// Directions
	int starsDir, moveDir; // background and character
	final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3; 

	// Other data: player position, enemy HP, player attack, boolean for if a robot was beat
	// weapon damages, weapon prices
	int y, x, HP1, HP2, HP3, ATK, counter;
	boolean beat1, beat2, beat3;
	final int[] ATKs = {1, 5, 20, 25, 50, 100, 1000, 2000, 10000};
	final int[] intPrices = {0, 10, 20, 5, 10, 20, 5, 10, 0};

	// ArrayLists: sorting, and background movement
	ArrayList <ImageView> castle, stars;
	ArrayList<String> names, names2;
	ArrayList<Integer> scores3;

	// Classes
	TitlePage title;
	Base base;
	Inventory inventory;
	Skins skin;
	Raid raid;
	Arrow arrow;
	Smithing smith;
	Highscore high;

	// Fonts
	Font shan, shanSmall, shan14, shan24, shan28, dance;

	// Pane
	Pane root, root4, root5, root6, root6a, root6b, root6c, root7;
	Scene scene;
	Stage stage;

	// GridPane
	GridPane root2;

	// Objects
	ImageView inventTitle, ivArcher, ivArcher2, ivHappy, ivRed, ivArtillery;
	Label lblName, lblRace, lblHappy, lblRed, lblArtillery, lblWealth;
	Label[] lblPrice, lblAmount, lblAchievements, lblTopNames, lblTopScores;
	TextArea txtStory;

	// Audio
	File forest, night, robot;
	Media Mforest, Mnight, Mrobot;
	MediaPlayer MPforest, MPnight, MProbot;
	AudioClip arrowSound;

	// Others
	AnimationTimer timer, timer2;
	Timeline arrowTimer, happyTimer, redTimer, artilleryTimer;
	File textFile;

	public void start(Stage primaryStage) throws IOException {
		try {

			//-------------------------------------------Close Window Event-------------------------------------------

			// If close window:

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {

					// Alert confirmation
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText(null);
					alert.setContentText("Are you sure want to exit?\nThis will be your final score.");
					alert.setTitle("Exit");

					// Change buttons
					alert.getButtonTypes().clear();
					alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

					// Get result
					Optional<ButtonType> result = alert.showAndWait();

					// Check result and exit or not
					if (result.get() == ButtonType.YES) { 

						//---------------------------------------------Write file---------------------------------------------

						// Write file to points.txt

						// Add current name and points
						names2.add(high.getName());
						scores3.add(high.getPoints());

						try { 

							// Create Writer and BufferedWriter streams for high score
							FileWriter out1 = new FileWriter(textFile);
							BufferedWriter writeFile1 = new BufferedWriter(out1);

							// Write sorted score to the text file
							for (int i = 0; i < scores3.size(); i++) {

								writeFile1.write(names2.get(i) + ", " + scores3.get(i));
								writeFile1.newLine();

							}

							// Close output streams
							writeFile1.close();
							out1.close();
						}
						// Catch exceptions
						catch (FileNotFoundException e) {
							System.out.println("File does not exist or not found");
							System.out.println("FileNotFoundException " + e.getMessage());
						}
						catch (IOException e) {
							System.out.println("Problem reading file.");
							System.out.println("IOException " + e.getMessage());
						}
						catch (Exception e) {
							System.out.println("Exception error" + e.getMessage());
						}

						//---------------------------------------------Quit---------------------------------------------
						// Exit program
						System.exit(0);

					}
					else if (result.get() == ButtonType.NO) {

						// Cancel event and return
						event.consume();
						return;

					}

				}});

			//---------------------------------------------Read file---------------------------------------------

			// Read in text into ArrayLists

			// Create file object
			textFile = new File("points.txt");
			FileReader in = new FileReader(textFile);
			BufferedReader readFile = new BufferedReader(in);

			// Variable to hold text
			String lineOfText;
			names = new ArrayList<String>();
			names2 = new ArrayList<String>();

			// Integer variables
			ArrayList<Integer> scores1 = new ArrayList<Integer>();
			ArrayList<Integer> scores2 = new ArrayList<Integer>();
			scores3 = new ArrayList<Integer>();

			// Initialize Arrays for input handling
			String[] split = new String[2];

			try { 

				// read all lines of text
				while ((lineOfText = readFile.readLine()) != null) {

					// Split text 
					split = lineOfText.split(", ");

					// Record data
					names.add(split[0]);
					scores1.add(Integer.parseInt(split[1]));
					scores2.add(Integer.parseInt(split[1]));

				}

				// Close reverse order
				readFile.close();
				in.close();

			}
			// Catch exceptions
			catch (FileNotFoundException e) {
				System.out.println("File does not exist or not found");
				System.out.println("FileNotFoundException " + e.getMessage());
			}
			catch (IOException e) {
				System.out.println("Problem reading file.");
				System.out.println("IOException " + e.getMessage());
			}
			catch (Exception e) {
				System.out.println("Exception error" + e.getMessage());
			}

			//---------------------------------------------Bubble Sort---------------------------------------------

			// Sort into ascending order by bubble sort

			// Initialize as not done
			boolean done = false;

			// Check all adjacent values
			for (int end = scores2.size() - 1; end > 0 && !done; end--) {

				// Initialize as done
				done = true;

				// Move heavier to bottom of list
				for (int i = 0; i < end; i++) {

					// If next index is greater
					if (scores2.get(i) > scores2.get(i+1)) {

						// Not done
						done = false;

						// Swap
						int temp = scores2.get(i);
						scores2.remove(i);
						scores2.add(i, scores2.get(i));
						scores2.remove(i+1);
						scores2.add(i+1, temp);

					}
				}

			}

			// Reverse result for descending order
			for (int i = scores2.size() - 1; i > -1; i--) {
				scores3.add(scores2.get(i));
			}

			// Update name positions - for size of sorted integer array
			for (int i = 0; i < scores3.size(); i++) {
				for (int j = 0; j < scores3.size(); j++) {
					if (scores3.get(i).toString().equals(scores1.get(j).toString())) { // direct integer comparison had bug
						names2.add(names.get(j));
					}
				}
			}

			//---------------------------------------------Data Initialization---------------------------------------------
			// Declare and initialize data

			// Declare font used
			shan = Font.loadFont("file:fonts/Ma_Shan_Zheng/MaShanZheng-Regular.ttf", 100);
			shanSmall = Font.loadFont("file:fonts/Ma_Shan_Zheng/MaShanZheng-Regular.ttf", 60);
			shan14 = Font.loadFont("file:fonts/Ma_Shan_Zheng/MaShanZheng-Regular.ttf", 14);
			shan24 = Font.loadFont("file:fonts/Ma_Shan_Zheng/MaShanZheng-Regular.ttf", 24);
			shan28 = Font.loadFont("file:fonts/Ma_Shan_Zheng/MaShanZheng-Regular.ttf", 28);
			dance = Font.loadFont("file:fonts/Dancing_Script/DancingScript.ttf", 20);

			// Initialize classes
			title = new TitlePage();
			base = new Base();
			inventory = new Inventory();
			skin = new Elf(EAST, 100);
			raid = new Raid();
			arrow = new Arrow();
			smith = new Smithing();
			high = new Highscore();

			// Initialize movement booleans
			starsDir = -1;
			moveDir = -1;

			// Initialize achievement booleans
			beat1 = false;
			beat2 = false;
			beat3 = false;

			// Initialize room booleans
			titlePage = true;
			homebase = false;
			inventoryRoom = false;
			skinRoom = false;
			raidRoom = false;
			smithRoom = false;
			highscoreRoom = false;

			// Initialize data variables
			x = 0;
			y = 300;
			HP1 = 5;
			HP2 = 100;
			HP3 = 10000;
			ATK = 1;
			counter = 0;

			// Audio files:

			// Forest background music
			forest = new File("audio/Autumn Forest.mp3");
			Mforest = new Media(forest.toURI().toString());
			MPforest = new MediaPlayer(Mforest);
			MPforest.setOnEndOfMedia(new Runnable() {
				public void run() {
					MPforest.seek(Duration.ZERO);
				}
			});

			// Night stars background music
			night = new File("audio/06 Flicker.mp3");
			Mnight = new Media(night.toURI().toString());
			MPnight = new MediaPlayer(Mnight);
			MPnight.setOnEndOfMedia(new Runnable() {
				public void run() {
					MPnight.seek(Duration.ZERO);
				}
			});

			// Robotic background music
			robot = new File("audio/ancient_robot.mp3");
			Mrobot = new Media(robot.toURI().toString());
			MProbot = new MediaPlayer(Mrobot);
			MProbot.setOnEndOfMedia(new Runnable() {
				public void run() {
					MProbot.seek(Duration.ZERO);
				}
			});

			// AudioClip for arrow shooting
			arrowSound = new AudioClip("file:audio/shoot.wav");

			// Adjust volumes
			MPforest.setVolume(0.5);
			MPnight.setVolume(0.3);
			MProbot.setVolume(0.5);
			arrowSound.setVolume(1);

			// Auto play first background music
			MPforest.setAutoPlay(true);

			//---------------------------------------------Pane Initializations---------------------------------------------
			// Initialize main Pane

			// Declare background image
			ImageView bgImg = title.getTitle();

			// Declare Pane and scene
			root = new Pane();
			scene = new Scene(root, bgImg.getImage().getWidth(), bgImg.getImage().getHeight());
			stage = primaryStage;

			//---------------------------------------------Title Page---------------------------------------------
			// Initialize objects for title page

			// Initialize background for options
			ImageView board = title.getBoard();

			// Declare ImageView for title banner
			ImageView banner = title.getBanner();

			// Initialize label for title
			Label lblTitle = new Label();
			lblTitle.setText("Elves Revolution");
			lblTitle.setFont(shan);
			lblTitle.setTextFill(Color.RED);
			lblTitle.setLayoutX(1375 / 2 - (947 - 443) / 2);
			lblTitle.setLayoutY(280);

			// Initialize label for start
			Button btnStart = new Button();
			btnStart.setText("Start");
			btnStart.setFont(shanSmall);
			btnStart.setTextFill(Color.GREEN);
			btnStart.setPrefWidth(200);
			btnStart.setLayoutX(1375 / 2 - 150 / 2 - 30);
			btnStart.setLayoutY(460);
			btnStart.setBackground(null);
			btnStart.setBorder(null);

			// Initialize label for Leader-board
			Button btnLead = new Button();
			btnLead.setText("Leaderboard");
			btnLead.setFont(shanSmall);
			btnLead.setTextFill(Color.GREEN);
			btnLead.setPrefWidth(400);
			btnLead.setLayoutX(478);
			btnLead.setLayoutY(460 + 95);
			btnLead.setBackground(null);
			btnLead.setBorder(null);

			// Initialize label for help
			Button btnHelp = new Button();
			btnHelp.setText("Help");
			btnHelp.setFont(shanSmall);
			btnHelp.setTextFill(Color.GREEN);
			btnHelp.setPrefWidth(200);
			btnHelp.setLayoutX(1375 / 2 - 150 / 2 - 30);
			btnHelp.setLayoutY(460 + 190);
			btnHelp.setBackground(null);
			btnHelp.setBorder(null);

			// Add elements to Pane
			root.getChildren().addAll(bgImg, banner, board, lblTitle, btnStart, btnLead, btnHelp);

			// Button Actions
			btnStart.setOnAction(e -> btn_start());
			btnLead.setOnAction(e -> btn_lead());
			btnHelp.setOnAction(e -> btn_help());

			//---------------------------------------------Home Base Page---------------------------------------------

			// Initialize objects for home base room

			// Initialize ArrayLists
			stars = new ArrayList<ImageView>();
			castle = new ArrayList<ImageView>();

			// If timer started
			timer = new AnimationTimer() {
				public void handle(long val) {

					// Move background
					if (starsDir == NORTH) {
						base.setYPos(base.NORTH);
					}
					else if (starsDir == SOUTH) {
						base.setYPos(base.SOUTH);
					}

					// Update yPos
					base.calcYPos();

					// Draw images
					for (int i = 0; i < base.getFloorNum(); i++) {		
						stars.get(i).setY(base.getStarsYPos(i));
						stars.get(i).setY(base.getStarsYPos(i));
						castle.get(i).setY(base.getCastleYPos(i));
					}

					for (int i = 2; i < 5; i++) {
						stars.get(i).setY(base.getStarsYPos(i));
					}

				}
			};

			//---------------------------------------------Key Actions---------------------------------------------

			// If keys are pressed
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle (KeyEvent e) {
					if (e.getEventType() == KeyEvent.KEY_PRESSED) {

						// Set background movement
						if (e.getCode() == KeyCode.UP && homebase == true) {
							starsDir = NORTH;
						}
						else if (e.getCode() == KeyCode.DOWN && homebase == true) {
							starsDir = SOUTH;
						}

						// Set character movement
						if (e.getCode() == KeyCode.UP && raidRoom == true) {
							moveDir = NORTH;
						}
						else if (e.getCode() == KeyCode.DOWN && raidRoom == true) {
							moveDir = SOUTH;
						}
						else if (e.getCode() == KeyCode.RIGHT && raidRoom == true) {
							moveDir = EAST;
						}
						else if (e.getCode() == KeyCode.LEFT && raidRoom == true) {
							moveDir = WEST;
						}

						// shoot Arrow 
						if (e.getCode() == KeyCode.SPACE) {
							if (arrow.isFired() == false) {
								arrow.setPosition(ivArcher2.getX(), ivArcher2.getY(), EAST);
								arrowSound.play();
								arrowTimer.play();
							}
						}
					}
				}
			});

			// Movement for Arrow
			KeyFrame kfArrow = new KeyFrame(Duration.millis(10), new
					EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					// Move arrow
					arrow.move();

					// Update arrow Image
					arrow.getNode();

					// Stop arrow if off screen
					if (arrow.isOffScreen(scene.getWidth())) {
						arrowTimer.stop();
					}
				}
			});
			// Initialize Time-line for arrow
			arrowTimer = new Timeline(kfArrow);
			arrowTimer.setCycleCount(Timeline.INDEFINITE);

			// If keys are released
			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle (KeyEvent e) {

					// Reset background movement
					if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
						starsDir = -1;
					}

					// Reset character movement
					if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN 
							|| e.getCode() == KeyCode.RIGHT|| e.getCode() == KeyCode.LEFT) {
						moveDir = -1;
					}

				}
			});

			//---------------------------------------------High-score Page---------------------------------------------

			// Initialize objects for the high score page

			// Initialize Pane
			root7 = new Pane();

			// Initialize background image
			ImageView ivLeadBack = high.getBack();

			// Add elements to Pane
			root7.getChildren().add(ivLeadBack);

			// Initialize label arrays for leader-board
			lblTopNames = new Label[5];
			lblTopScores = new Label[5];

			// Add top of leader-board to Pane
			for (int i = 0; i < 5; i++) {

				// Names: left
				lblTopNames[i] = new Label();
				lblTopNames[i].setFont(dance);
				lblTopNames[i].setLayoutX(177);
				lblTopNames[i].setLayoutY(165 + 63 * i);
				lblTopNames[i].setTextFill(Color.RED);
				lblTopNames[i].setText(names2.get(i));

				// Scores: right
				lblTopScores[i] = new Label();
				lblTopScores[i].setText(scores3.get(i).toString());
				lblTopScores[i].setFont(dance);
				lblTopScores[i].setLayoutX(300);
				lblTopScores[i].setLayoutY(160 + 63 * i);
				lblTopScores[i].setTextFill(Color.RED);
				lblTopScores[i].setPrefSize(140, 40);
				lblTopScores[i].setAlignment(Pos.CENTER_RIGHT);

				// Add labels to sub Pane
				root7.getChildren().addAll(lblTopNames[i], lblTopScores[i]);
			}

			//---------------------------------------------Inventory Page---------------------------------------------

			// Initialize objects in the inventory page

			// Declare GridPane 
			root2 = new GridPane();
			root.setStyle("-fx-background-color: black");
			//root2.setGridLinesVisible(true);

			// Declare GridPane constraints
			RowConstraints row1 = new RowConstraints(144);
			RowConstraints row2 = new RowConstraints(233);
			RowConstraints row3 = new RowConstraints(100);

			ColumnConstraints col1 = new ColumnConstraints(804);

			// Add Constraints to GridPane
			root2.getColumnConstraints().add(col1);
			root2.getRowConstraints().addAll(row1, row2, row3);

			//----------------------------------------------------------------------------------------------------

			// Initialize banner background Image and title Label for title

			// Declare main banner
			inventTitle = inventory.getBanner();

			// Initialize Label for title
			Label lblInventTitle = new Label();
			lblInventTitle.setText("Inventory");
			lblInventTitle.setFont(shan28);
			lblInventTitle.setTextFill(Color.YELLOW);

			//----------------------------------------------------------------------------------------------------

			// Create VBox for 2 HBoxes of 6 storage Image each

			// Declare Array of ImageViews
			ImageView[] ivStorage = new ImageView[12];

			// Add images to array
			for (int i = 0; i < 12; i++) {
				ivStorage[i] = new ImageView(inventory.getBackground().getImage());
				ivStorage[i].setPreserveRatio(true);
				ivStorage[i].setFitWidth(100);
			}

			// Declare VBox
			VBox vbStorage = new VBox();
			vbStorage.setSpacing(20);
			vbStorage.setAlignment(Pos.CENTER);

			// Declare HBox #1
			HBox hbStorage1 = new HBox();
			hbStorage1.setSpacing(30);
			hbStorage1.setAlignment(Pos.CENTER);

			// Declare HBox #2
			HBox hbStorage2 = new HBox();
			hbStorage2.setSpacing(30);
			hbStorage2.setAlignment(Pos.CENTER);

			// Add images to HBox
			for (int j = 0; j < 6; j++) {

				hbStorage1.getChildren().add(ivStorage[j]);
				hbStorage2.getChildren().add(ivStorage[j + 6]);

			}

			// Add HBoxes to VBox
			vbStorage.getChildren().addAll(hbStorage1, hbStorage2);

			//----------------------------------------------------------------------------------------------------

			// Create HBox of banner background Images and title Labels

			// Declare HBox
			HBox HBoxSubbanners = new HBox();
			HBoxSubbanners.setSpacing(50);
			HBoxSubbanners.setAlignment(Pos.CENTER);

			// Declare Array of images
			ImageView[] ivSubbanners = new ImageView[3];

			// Add images to ImageView
			for (int i = 0; i < 3; i++) {
				ivSubbanners[i] = new ImageView(inventory.getSubBanner().getImage());
				ivSubbanners[i].setPreserveRatio(true);
				ivSubbanners[i].setFitWidth(200);
				HBoxSubbanners.getChildren().add(ivSubbanners[i]);
			}

			// Declare HBox
			HBox hbSubtitles = new HBox();
			hbSubtitles.setSpacing(185);
			hbSubtitles.setAlignment(Pos.CENTER);

			// Declare Array of subtitles
			Label[] lblSubtitles = new Label[3];

			// Initialize subtitle labels
			for (int i = 0; i < 3; i++) {
				lblSubtitles[i] = new Label();
				lblSubtitles[i].setFont(shan24);
				lblSubtitles[i].setTextFill(Color.YELLOW);
			}

			// Initialize texts
			lblSubtitles[0].setText("Weapons");
			lblSubtitles[1].setText("Materials");
			lblSubtitles[2].setText("Others");

			// Add labels to HBox
			hbSubtitles.getChildren().addAll(lblSubtitles[0], lblSubtitles[1], lblSubtitles[2]);

			//----------------------------------------------------------------------------------------------------

			// Add an exit mark
			ImageView ivExit = inventory.getCancel();

			//----------------------------------------------------------------------------------------------------

			// Add objects to GridPane

			// Add board to GridPane (0,0), center
			GridPane.setValignment(inventTitle, VPos.CENTER);
			GridPane.setHalignment(inventTitle, HPos.CENTER);
			root2.add(inventTitle, 0, 0);

			// Add Label title onto (0,0), center
			GridPane.setValignment(lblInventTitle, VPos.CENTER);
			GridPane.setHalignment(lblInventTitle, HPos.CENTER);
			root2.add(lblInventTitle, 0, 0);

			// Add VBox storage onto (0, 1), center
			GridPane.setValignment(vbStorage, VPos.CENTER);
			GridPane.setHalignment(vbStorage, HPos.CENTER);
			root2.add(vbStorage, 0, 1);

			// Add HBox subtitle background Images to (0, 2), center
			GridPane.setValignment(HBoxSubbanners, VPos.CENTER);
			GridPane.setHalignment(HBoxSubbanners, HPos.CENTER);
			root2.add(HBoxSubbanners, 0, 2);

			// Add HBox subtitles Labels to (0, 2), center
			GridPane.setValignment(hbSubtitles, VPos.CENTER);
			GridPane.setHalignment(hbSubtitles, HPos.CENTER);
			GridPane.setMargin(hbSubtitles, new Insets(0, 10, 0, 0));
			root2.add(hbSubtitles, 0, 2);

			// Add exit mark, right aligned
			GridPane.setValignment(ivExit, VPos.CENTER);
			GridPane.setHalignment(ivExit, HPos.RIGHT);
			root2.add(ivExit, 0, 0);

			//----------------------------------------------------------------------------------------------------

			// Pane to store images

			// Declare main Pane
			root6 = new Pane();

			// Declare sub Panes
			root6a = new Pane();
			root6b = new Pane();
			root6c = new Pane();

			//----------------------------------------------------------------------------------------------------
			// Pane a - weapons

			// Initialize array of bow ImageView
			ImageView[] bowsOwned = new ImageView[9];

			// Add bows to array
			for (int i = 0; i < 9; i++) {
				bowsOwned[i] = new ImageView(smith.getBow(i).getImage());
				bowsOwned[i].setPreserveRatio(true);
				bowsOwned[i].setFitHeight(60);
				bowsOwned[i].setX(40 + 130 * (i % 6));
				bowsOwned[i].setY(167 + (int)(i / 6) * 120);

				if (inventory.getStored(0, i) == 1) {
					root6a.getChildren().add(bowsOwned[i]);					
				}
			}

			// Add sub Pane to main Pane
			root6.getChildren().add(root6a);

			//----------------------------------------------------------------------------------------------------
			// Pane b - materials

			// Initialize array for material images
			ImageView[] materials = new ImageView[3];

			// Initialize array to label amount
			lblAmount = new Label[3];

			// Add materials to array
			for (int i = 0; i < 3; i++) {

				// Images
				materials[i] = new ImageView();
				materials[i].setPreserveRatio(true);
				materials[i].setFitHeight(60);
				materials[i].setX(40 + 130 * i);
				materials[i].setY(167);

				// Labels
				lblAmount[i] = new Label();
				lblAmount[i].setText(Integer.toString(inventory.getStored(1, i)));
				lblAmount[i].setFont(shan24);
				lblAmount[i].setLayoutX(87 + 130 * i);
				lblAmount[i].setLayoutY(220);
				lblAmount[i].setTextFill(Color.RED);

				// Add elements to Pane
				root6b.getChildren().addAll(materials[i], lblAmount[i]);

			}

			// Initialize images for materials
			materials[0].setImage(new Image("file:images/ores1.png"));
			materials[1].setImage(new Image("file:images/ores2.png"));
			materials[2].setImage(new Image("file:images/ores3.png"));

			//----------------------------------------------------------------------------------------------------
			// Pane c - achievements

			// Initialize array of label
			lblAchievements = new Label[3];

			// Initialize achievement labels
			for (int i = 0; i < 3; i++) {
				lblAchievements[i] = new Label();
				lblAchievements[i].setFont(shan14);
				lblAchievements[i].setTextFill(Color.YELLOW);
				lblAchievements[i].setPrefSize(70, 60);
				lblAchievements[i].setAlignment(Pos.CENTER);
				lblAchievements[i].setLayoutX(35 + 130 * i);
				lblAchievements[i].setLayoutY(167);
			}

			// Set achievement names
			lblAchievements[0].setText("Achievement\nFirst Blood");
			lblAchievements[1].setText("Achievement\nRookie Ranger");
			lblAchievements[2].setText("Achievement\nEagleshooter");

			//---------------------------------------------Skins Page---------------------------------------------

			// Create sub Pane
			Pane root3 = new Pane();

			// Initialize background image
			ImageView ivSkinBack = skin.getBack();

			// Initialize text board image
			ImageView ivTextBoard = skin.getBoard();

			// Initialize exit image
			ImageView ivExit2 = new ImageView(inventory.getCancel().getImage());
			ivExit2.setX(1280);
			ivExit2.setY(20);

			// Initialize archer Image
			ivArcher = new ImageView(new Image("file:images/elf1.png"));
			ivArcher.setPreserveRatio(true);
			ivArcher.setFitHeight(441);
			ivArcher.setX(200);
			ivArcher.setY(187);

			// Initialize name label
			lblName = new Label();
			lblName.setText(skin.getName());
			lblName.setFont(dance);
			lblName.setLayoutX(950);
			lblName.setLayoutY(238);
			lblName.setTextFill(Color.SPRINGGREEN);

			// Initialize Race label
			lblRace = new Label();
			lblRace.setText(skin.getRace());
			lblRace.setFont(dance);
			lblRace.setLayoutX(1160);
			lblRace.setLayoutY(238);
			lblRace.setTextFill(Color.SPRINGGREEN);

			// Initialize text area for back story
			txtStory = new TextArea();
			txtStory.setText(skin.getStory());
			txtStory.setWrapText(true);
			txtStory.setLayoutX(934);
			txtStory.setLayoutY(325);
			txtStory.setPrefSize(387, 205);
			txtStory.setStyle("-fx-text-fill: Springgreen");
			txtStory.setEditable(false);
			txtStory.setFont(dance);

			// Add images and text to Pane
			root3.getChildren().addAll(ivSkinBack, ivTextBoard, ivArcher, lblName, lblRace, txtStory, ivExit2);

			// Initialize Buttons
			Button[] buttons = new Button[3];
			for (int i = 0; i < 3; i++) {
				buttons[i] = new Button();
				buttons[i].setText("Archer " + (i + 1));
				buttons[i].setFont(dance);
				buttons[i].setPrefSize(100, 30);
				buttons[i].setLayoutX(20);
				buttons[i].setLayoutY(300 + 50 * i);
				root3.getChildren().add(buttons[i]);
			}

			// Button actions
			buttons[0].setOnAction(e -> change_char(0));
			buttons[1].setOnAction(e -> change_char(1));
			buttons[2].setOnAction(e -> change_char(2));

			//---------------------------------------------Raid Page--------------------------------------------

			// Create sub Pane
			root4 = new Pane();

			// Initialize background Image
			ImageView ivRaidBack = raid.getBack();

			// Initialize home button image
			ImageView ivHome = raid.getHome();
			ivHome.setX(942);
			ivHome.setY(20);

			// Initialize archer image and position
			ivArcher2 = new ImageView(new Image("file:images/elf1R.png"));
			ivArcher2.setPreserveRatio(true);
			ivArcher2.setFitHeight(100);

			// Initialize robot - happy
			ivHappy = raid.getHappy();
			ivHappy.setX(840);
			ivHappy.setY(50);

			// Initialize robot - red
			ivRed = raid.getRed();
			ivRed.setX(840);
			ivRed.setY(250);

			// Initialize robot - artillery
			ivArtillery = raid.getArtillery();
			ivArtillery.setX(780);
			ivArtillery.setY(470);

			// Initialize HP label - happy
			lblHappy = new Label();
			lblHappy.setText("HP: " + HP1 + "/5");
			lblHappy.setPrefSize(77, 20);
			lblHappy.setLayoutX(845);
			lblHappy.setLayoutY(45);
			lblHappy.setStyle("-fx-background-color: red");
			lblHappy.setTextFill(Color.WHITE);
			lblHappy.setAlignment(Pos.CENTER);

			// Initialize HP label - red
			lblRed = new Label();
			lblRed.setText("HP: " + HP2 + "/100");
			lblRed.setPrefSize(120, 30);
			lblRed.setLayoutX(825);
			lblRed.setLayoutY(230);
			lblRed.setStyle("-fx-background-color: red");
			lblRed.setTextFill(Color.WHITE);
			lblRed.setAlignment(Pos.CENTER);

			// Initialize HP label - artillery
			lblArtillery = new Label();
			lblArtillery.setText("HP: " + HP3 + "/10000");
			lblArtillery.setPrefSize(200, 36);
			lblArtillery.setLayoutX(800);
			lblArtillery.setLayoutY(475);
			lblArtillery.setStyle("-fx-background-color: red");
			lblArtillery.setTextFill(Color.WHITE);
			lblArtillery.setAlignment(Pos.CENTER);


			// Add elements to Pane
			root4.getChildren().addAll(ivRaidBack, ivArcher2, arrow.getNode(), ivHappy, ivRed, ivArtillery, 
					lblHappy, lblRed, lblArtillery, ivHome);

			// If timer2 started
			timer2 = new AnimationTimer() {
				public void handle(long val) {

					// Update position
					ivArcher2.setX(x);
					ivArcher2.setY(y);

					// Check movement
					if (moveDir != -1) {
						move(moveDir);						
					}

					// Check out of bounds
					if (x < 0) {
						x = 0;
						ivArcher2.setX(0);
					}
					else if (x > 340) {
						x = 340;
						ivArcher2.setX(x);
					}
					else if (y < 0) {
						y = 0;
						ivArcher2.setY(y);
					}
					else if (y > 700 - 100) {
						y = 600;
						ivArcher2.setY(y);
					}

					// Collisions - Subtract HP by attack, stop arrow, update HP
					if (arrow.getNode().getBoundsInParent().intersects(ivHappy.getBoundsInParent())) {
						HP1 -= ATK;
						high.addPoints(ATK);
						arrow.stopArrow();
						lblHappy.setText("HP: " + HP1 + "/5");
					}
					else if (arrow.getNode().getBoundsInParent().intersects(ivRed.getBoundsInParent())) {
						HP2 -= ATK;
						high.addPoints(ATK);
						arrow.stopArrow();
						lblRed.setText("HP: " + HP2 + "/100");
					}
					else if (arrow.getNode().getBoundsInParent().intersects(ivArtillery.getBoundsInParent())) {
						HP3 -= ATK;
						high.addPoints(ATK);
						arrow.stopArrow();
						lblArtillery.setText("HP: " + HP3 + "/10000");
					}

					// Check Robots health: if dead, remove from Pane, play re-spawn Time-line, set position
					if (HP1 <= 0) {
						root4.getChildren().removeAll(ivHappy, lblHappy);
						happyTimer.play();
						ivHappy.setX(-300);
						ivHappy.setY(-300);
					}
					else if (HP2 <= 0) {
						root4.getChildren().removeAll(ivRed, lblRed);
						redTimer.play();
						ivRed.setX(-300);
						ivRed.setY(-300);
					}
					else if (HP3 <= 0) {
						root4.getChildren().removeAll(ivArtillery, lblArtillery);
						artilleryTimer.play();
						ivArtillery.setX(-300);
						ivArtillery.setY(-300);
					}

					// Add ores - only one gain per enemy won
					if (root4.getChildren().contains(ivHappy) == false && counter == 0) {
						inventory.setStored(1, 0, inventory.getStored(1, 0) + 1);
						counter = 1;
					}
					else if (root4.getChildren().contains(ivRed) == false && counter == 0) {
						inventory.setStored(1, 1, inventory.getStored(1, 1) + 1);
						counter = 1;
					}
					else if (root4.getChildren().contains(ivArtillery) == false && counter == 0) {
						inventory.setStored(1, 2, inventory.getStored(1, 2) + 1);
						counter = 1;
					}

					// Add achievement - once gained once per enemy
					if (root4.getChildren().contains(ivHappy) == false && beat1 == false) {
						inventory.setStored(2, 0, 1);
						beat1 = true;
					}
					else if (root4.getChildren().contains(ivRed) == false && beat2 == false) {
						inventory.setStored(2, 1, 1);
						beat2 = true;
					}
					else if (root4.getChildren().contains(ivArtillery) == false && beat3 == false) {
						inventory.setStored(2, 2, 1);
						beat3 = true;
					}

				}
			};

			// Re-spawn robot every second
			KeyFrame kfHappy = new KeyFrame(Duration.millis(1000), new
					EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					// Reset health, position, counter, add back to Pane
					HP1 = 5;
					lblHappy.setText("HP: " + HP1 + "/5");
					ivHappy.setX(840);
					ivHappy.setY(50);
					root4.getChildren().addAll(ivHappy, lblHappy);
					counter = 0;

				}
			});
			happyTimer = new Timeline(kfHappy);
			happyTimer.setCycleCount(1);

			// Re-spawn robot every 2 seconds
			KeyFrame kfRed = new KeyFrame(Duration.millis(2000), new
					EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					// Reset health, position, counter, add back to Pane
					HP2 = 100;
					lblRed.setText("HP: " + HP2 + "/100");
					ivRed.setX(840);
					ivRed.setY(250);
					root4.getChildren().addAll(ivRed, lblRed);
					counter = 0;

				}
			});
			redTimer = new Timeline(kfRed);
			redTimer.setCycleCount(1);

			// Re-spawn robot every minute
			KeyFrame kfArtillery = new KeyFrame(Duration.millis(60000), new
					EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					// Reset health, position, counter, add back to Pane
					HP3 = 10000;
					lblArtillery.setText("HP: " + HP3 + "/10000");
					ivArtillery.setX(780);
					ivArtillery.setY(470);
					root4.getChildren().addAll(ivArtillery, lblArtillery);
					counter = 0;

				}
			});
			artilleryTimer = new Timeline(kfArtillery);
			artilleryTimer.setCycleCount(1);

			//---------------------------------------------Smith Page---------------------------------------------
			// Initialize objects for smith page

			// Initialize Pane
			root5 = new Pane();

			// Initialize background
			ImageView ivShelf = new ImageView(new Image("file:images/shelf.png"));

			// Initialize label for ores owned
			lblWealth = new Label();
			lblWealth.setText("YOU HAVE: " + inventory.getStored(1, 0) + "s, " + inventory.getStored(1, 1) + "q, " +
					inventory.getStored(1, 2) + "g");
			lblWealth.setLayoutX(240);
			lblWealth.setLayoutY(24);
			lblWealth.setPrefSize(240, 30);
			lblWealth.setStyle("-fx-background-color: white");
			lblWealth.setTextFill(Color.RED);
			lblWealth.setFont(dance);
			lblWealth.setAlignment(Pos.CENTER);

			// Initialize exit image
			ImageView ivExit3 = new ImageView(inventory.getCancel().getImage());
			ivExit3.setX(620);
			ivExit3.setY(20);

			// Add elements to Pane
			root5.getChildren().addAll(ivShelf, lblWealth, ivExit3);

			// Initialize array of ImageView
			ImageView[] bows = new ImageView[9];

			// Initialize array of labels
			lblPrice = new Label[9];

			// Initialize array of prices for labels
			String[] prices = {"owned", "10s", "20s", "5q", "10q", "20q", "5g", "10g", "20g"};

			// Add bows
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {

					// Initialize bow positions
					bows[i * 3 + j] = smith.getBow(i * 3 + j);
					bows[i * 3 + j].setX(127 + 180 * j);
					bows[i * 3 + j].setY(62 + 200 * i);
					root5.getChildren().add(bows[i * 3 + j]);

					// Initialize labels
					lblPrice[i * 3 + j] = new Label();
					lblPrice[i * 3 + j].setText(prices[i * 3 + j]);
					lblPrice[i * 3 + j].setLayoutX(127 + 180 * j);
					lblPrice[i * 3 + j].setLayoutY(180 + 200 * i);
					lblPrice[i * 3 + j].setPrefSize(100, 30);
					lblPrice[i * 3 + j].setStyle("-fx-background-color: white");
					lblPrice[i * 3 + j].setTextFill(Color.RED);
					lblPrice[i * 3 + j].setFont(shan24);
					lblPrice[i * 3 + j].setAlignment(Pos.CENTER);
					root5.getChildren().add(lblPrice[i * 3 + j]);

				}
			}

			//---------------------------------------------Mouse Actions---------------------------------------------
			// If mouse is clicked
			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle (MouseEvent e) {
					if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {

						//---------------------------------------------Inventory------------------------------------------

						// Switch from home base to inventory screen
						if (homebase == true && e.getX() > 109 && e.getX() < 216 && 
								e.getY() > 467 + base.getYPos() && e.getY() < 561 + base.getYPos()) {

							// Clear Pane, add GridPane to Pane, change stage dimension, center
							root.getChildren().clear();
							root.getChildren().addAll(root2, root6);
							stage.setWidth(804);
							stage.setHeight(500);
							stage.centerOnScreen();

							// Stop timer
							timer.stop();

							// Change room booleans
							homebase = false;
							inventoryRoom = true;

							// Update wealth
							for (int i = 0; i < 3; i++) {
								lblAmount[i].setText(Integer.toString(inventory.getStored(1, i)));
							}

							// Clear achievements
							root6c.getChildren().clear();

							// Update achievements
							if (beat1 == true) {
								root6c.getChildren().add(lblAchievements[0]);
							}
							if (beat2 == true) {
								root6c.getChildren().add(lblAchievements[1]);
							}
							if (beat3 == true) {
								root6c.getChildren().add(lblAchievements[2]);
							}

						}

						//---------------------------------------------In Inventory------------------------------------------

						// Swapping categories

						// If clicked on weapons
						if (root.getChildren().contains(root6) == true && e.getX() > 55 && e.getX() < 250 && 
								e.getY() > 408 && e.getY() < 448) {

							// Show sub Pane a
							root6.getChildren().clear();
							root6.getChildren().add(root6a);
						}

						// If clicked on materials
						else if (root.getChildren().contains(root6) == true && e.getX() > 306 && e.getX() < 498 && 
								e.getY() > 407 && e.getY() < 448) {

							// Show sub Pane b
							root6.getChildren().clear();
							root6.getChildren().add(root6b);
						}

						// If clicked on others
						else if (root.getChildren().contains(root6) == true && e.getX() > 557 && e.getX() < 750 && 
								e.getY() > 408 && e.getY() < 448) {

							// Show sub Pane c
							root6.getChildren().clear();
							root6.getChildren().add(root6c);
						}

						//---------------------------------------------Forging------------------------------------------

						// Switch from home base to inventory screen
						if (homebase == true && e.getX() > 407 && e.getX() < 516 && 
								e.getY() > 466 + base.getYPos() && e.getY() < 560 + base.getYPos()) {

							// Clear Pane, add GridPane to Pane, change stage dimension, center
							root.getChildren().clear();
							root.getChildren().add(root5);
							stage.setWidth(741);
							stage.setHeight(640);
							stage.centerOnScreen();

							// Update wealth text
							lblWealth.setText("YOU HAVE: " + inventory.getStored(1, 0) + "s, " + inventory.getStored(1, 1) + "q, " +
									inventory.getStored(1, 2) + "g");

							// Stop timer
							timer.stop();

							// Change room booleans
							homebase = false;
							smithRoom = true;

						}

						//---------------------------------------------In Forging------------------------------------------

						// Check if any item is clicked
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 3; j++) {

								// If clicked on item
								if (root.getChildren().contains(root5) && e.getX() > (127 + 180 * j) && e.getX() < (127 + 180 * j + 100) && 
										e.getY() > (62 + 200 * i) && e.getY() < (62 + 200 * i + 100)) {

									// Check if owned
									if (inventory.getStored(0, 3 * i + j) == 1) {
										break;
									}

									// Check if ores are sufficient
									else if (inventory.getStored(1, i) < intPrices[3 * i + j]) {
										break;
									}

									// Else process purchase
									else {

										// Take away ores, give weapon, update texts, update inventory
										inventory.ChangeStored(1, i, (-1) * intPrices[3 * i + j]);
										inventory.setStored(0, 3 * i + j, 1);
										ATK = ATKs[3 * i + j];
										lblWealth.setText("YOU HAVE: " + inventory.getStored(1, 0) + "s, " + 
												inventory.getStored(1, 1) + "q, " + inventory.getStored(1, 2) + "g");
										lblPrice[3 * i + j].setText("owned");
										root6a.getChildren().add(bowsOwned[3 * i + j]);
									}

								}

							}
						}

						//---------------------------------------------Skins------------------------------------------

						// Switch from home base to skins screen
						if (homebase == true && e.getX() > 136 && e.getX() < 231 && 
								e.getY() > 287 + base.getYPos() && e.getY() < 375 + base.getYPos()) {

							// Clear Pane, add GridPane to Pane, change stage dimension, center
							root.getChildren().clear();
							root.getChildren().add(root3);
							stage.setWidth(1400);
							stage.setHeight(700);
							stage.centerOnScreen();

							// Stop timer
							timer.stop();

							// Change room booleans
							homebase = false;
							skinRoom = true;

						}

						//---------------------------------------------High-score------------------------------------------

						// Switch from home base to high-score screen
						if (homebase == true && e.getX() > 389 && e.getX() < 485 && 
								e.getY() > 290 + base.getYPos() && e.getY() < 375 + base.getYPos()) {

							// Clear Pane, add GridPane to Pane, change stage dimension, center
							root.getChildren().clear();
							root.getChildren().add(root7);
							stage.setWidth(600);
							stage.setHeight(600);
							stage.centerOnScreen();

							// Stop timer
							timer.stop();

							// Change room booleans
							homebase = false;
							titlePage = false;
							highscoreRoom = true;

						}

						//---------------------------------------------Home Base------------------------------------------

						// Return to home base
						if ((skinRoom == true && e.getX() > 1280 && e.getX() < 1380 && e.getY() > 20 && e.getY() < 120) 
								|| (inventoryRoom = true && e.getX() > 713 && e.getX() < 794 && e.getY() > 33 && e.getY() < 109)
								|| (raidRoom = true && e.getX() > 942 && e.getX() < 1019 && e.getY() > 40 && e.getY() < 102)
								|| (smithRoom = true && e.getX() > 620 && e.getX() < 720 && e.getY() > 20 && e.getY() < 120)
								|| (highscoreRoom = true && e.getX() > 173 && e.getX() < 256 && e.getY() > 484 
								&& e.getY() < 512)) {

							// Change Background music
							MProbot.stop();
							MPnight.play();


							// Change room
							skinRoom = false;
							raidRoom = false;
							inventoryRoom = false;
							smithRoom = false;
							highscoreRoom = false;
							homebase = true;

							// Change images
							root.getChildren().clear();

							// Pane
							stage.setWidth(612);
							stage.setHeight(700);
							stage.centerOnScreen();

							// Add castle and stars images
							for (int i = 0; i < base.getFloorNum() + 2; i++) {
								stars.add(base.getIvS(i));
								stars.get(i).setX(0);
								root.getChildren().add(stars.get(i));
							}

							for (int i = 0; i < base.getFloorNum(); i++) {
								castle.add(base.getIvC(i));
								castle.get(i).setX(73);
								root.getChildren().add(castle.get(i));
							}

							// Start animation 
							timer.start();

							// Stop timer
							timer2.stop();

						}

						//---------------------------------------------Raid------------------------------------------

						// Switch from home base to raid screen
						if (homebase == true && e.getX() > 242 && e.getX() < 380 && 
								e.getY() > 500 + base.getYPos() && e.getY() < 645 + base.getYPos()) {

							// Alert
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Raid");
							alert.setHeaderText(null);
							alert.setContentText(
									"Here lies robots of various strengths. Due to the proximity of this place with"
											+ " Yggdrasil, all of the robots are paralyzed. Please do not leave the safe zone"
											+ " marked by the wooden fences. Best of luck.");
							alert.showAndWait();

							// Change Background music
							MPnight.stop();
							MProbot.play();

							// Clear Pane, add GridPane to Pane, change stage dimension, center
							root.getChildren().clear();
							root.getChildren().add(root4);
							stage.setWidth(1052);
							stage.setHeight(700);
							stage.centerOnScreen();

							// Change room booleans
							homebase = false;
							raidRoom = true;

							// Start animation
							timer2.start();

							// Initialize position
							x = 0;
							y = 300;

							// Reset robot health
							HP1 = 5;
							HP2 = 100;
							HP3 = 10000;

							lblHappy.setText("HP: " + HP1 + "/5");
							lblRed.setText("HP: " + HP2 + "/100");
							lblArtillery.setText("HP: " + HP3 + "/10000");

						}

					}
				}
			});

			//---------------------------------------------Scene---------------------------------------------
			stage.setTitle("Elves Revolution");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	//---------------------------------------------Methods---------------------------------------------
	// If start button is pressed
	private void btn_start() {

		// Confirm option, switch to home base room, initialize data

		// Confirm Alert
		Platform.runLater(new Runnable() {
			public void run() {

				// Initialize alert
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm Start");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to start a new game?");

				// Custom buttons
				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

				// Decision and show alert
				Optional<ButtonType> result = alert.showAndWait();

				// If yes
				if (result.get() == ButtonType.YES) {

					//-----------------------------------Ask name----------------------------------
					// Show dialog question
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Text Input Dialog");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter your name:");

					// result
					Optional<String> result2 = dialog.showAndWait();

					// if input
					if (result2.isPresent()) {
						String name = result2.get();
						high.setName(name);
					}

					//-----------------------------------Story----------------------------------
					// Initialize alert
					Alert alert3 = new Alert(AlertType.INFORMATION);
					alert3.setTitle("Back Story");
					alert3.setHeaderText(null);
					alert3.setContentText(
							"Once upon a time, Elves and humans lived peacefully together under the "
									+ "protection of the mother tree, Yggdrasil.");
					alert3.showAndWait();

					// Initialize alert
					Alert alert4 = new Alert(AlertType.INFORMATION);
					alert4.setTitle("Back Story");
					alert4.setHeaderText(null);
					alert4.setContentText(
							"But then, the robots invaded. Warfare burned many forests and many"
									+ " people lost their homes during this time. Yet the robots only wanted "
									+ "more resources.");
					alert4.showAndWait();

					// Initialize alert
					Alert alert5 = new Alert(AlertType.INFORMATION);
					alert5.setTitle("Back Story");
					alert5.setHeaderText(null);
					alert5.setContentText(
							"Will you be a part in defeating the robots and restoring the glory of"
									+ " Yggdrasil and nature?");
					alert5.showAndWait();

					//-----------------------------------Change room----------------------------------
					// Change room
					titlePage = false;
					homebase = true;

					// Change images
					root.getChildren().clear();

					// Pane
					stage.setWidth(612);
					stage.setHeight(700);
					stage.centerOnScreen();

					// Add castle and stars images
					for (int i = 0; i < base.getFloorNum() + 2; i++) {
						stars.add(base.getIvS(i));
						stars.get(i).setX(0);
						root.getChildren().add(stars.get(i));
					}

					for (int i = 0; i < base.getFloorNum(); i++) {
						castle.add(base.getIvC(i));
						castle.get(i).setX(73);
						root.getChildren().add(castle.get(i));
					}

					// Start animation 
					timer.start();

					// Change Background music
					MPforest.stop();
					MPnight.play();

					// Initialize alert
					Alert alert6 = new Alert(AlertType.INFORMATION);
					alert6.setTitle("Back Story");
					alert6.setHeaderText(null);
					alert6.setContentText(
							"To start off your journey of fighting the robots, you can choose an archer"
									+ " from the 'Skins' area to use. To fight robots, simply click the door "
									+ "under 'Raid'. Through fighting monsters, you will gain materials that "
									+ "can be used to forge better weapons at the 'Smithing'. You can always"
									+ " check your collection of weapons, materials, and achievements in "
									+ "'Inventory'. Lastly, there is a leaderboard that you may be able to go "
									+ "on when you end the game (by closing the window; pressing 'X')."
									+ " Good luck adventurer!");
					alert6.showAndWait();

				}
				else {
					return;
				}

			}
		});
	}

	// Button for leader-board
	private void btn_lead() {

		// Change Background music
		MPforest.stop();
		MPnight.setAutoPlay(true);

		// Clear Pane, add GridPane to Pane, change stage dimension, center
		root.getChildren().clear();
		root.getChildren().add(root7);
		stage.setWidth(600);
		stage.setHeight(600);
		stage.centerOnScreen();

		// Stop timer
		timer.stop();

		// Change room booleans
		homebase = false;
		titlePage = false;
		highscoreRoom = true;
	}

	// If help button is pressed
	private void btn_help() {

		// Alerts
		Platform.runLater(new Runnable() {
			public void run() {

				// Initialize alert
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Help");
				alert.setHeaderText(null);
				alert.setContentText(
						"To access buildings, just click on them.");
				alert.showAndWait();

				// Initialize alert
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Help");
				alert2.setHeaderText(null);
				alert2.setContentText(
						"Walk around with arrow keys, align yourself with the enemy's height to shoot"
								+ " precisely.");
				alert2.showAndWait();

				// Initialize alert
				Alert alert3 = new Alert(AlertType.INFORMATION);
				alert3.setTitle("Help");
				alert3.setHeaderText(null);
				alert3.setContentText(
						"Try using the up arrow key in the home base :)");
				alert3.showAndWait();
			}

		});

	}

	// Change character based on button
	public void change_char(int character) {

		// Change character class by selection
		if (character == 0) {
			ivArcher.setImage(new Image("file:images/elf1.png"));
			ivArcher2.setImage(new Image("file:images/elf1.png"));
			skin = new Elf(EAST, 100);
		}
		else if (character == 1) {
			ivArcher.setImage(new Image("file:images/undead archer1.png"));
			ivArcher2.setImage(new Image("file:images/undead archer1.png"));
			skin = new Undead(EAST, 100);
		}
		else if (character == 2) {
			ivArcher.setImage(new Image("file:images/cloak archer.png"));
			ivArcher2.setImage(new Image("file:images/cloak archer.png"));
			skin = new Cloakman(EAST, 100);
		}

		// Update information
		lblName.setText(skin.getName());
		lblRace.setText(skin.getRace());
		txtStory.setText(skin.getStory());


	}

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

		ivArcher2.setX(x);
		ivArcher2.setY(y);

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

	// Get xPos
	public int getX() {
		return x;
	}

	// Get yPos
	public int getY() {
		return y;
	}

}
