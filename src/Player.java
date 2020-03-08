import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * The Player class
 * This class manages all player movement and physics
 * as well as Collision detection and player texture loading
 */
public class Player {

	//The JLabel containing the player
	JLabel PlayerLabel;
	//The Array containing all the texures for the player label
	//0=Standing texture, 1=Ducking texture
	ImageIcon[] playerTex;

	//The index in the texture array of the currently shown texture
	int currentTex, 
	//The position of the ground in the Panel
	groundpos;
	
	//The velocity in the Y direction
	double yvel,
	//The position of the Label in the panel
	ypos;
	
	//The time the Game had started
	//If value is -1, the value has not been recieved yet
	public long startTime = -1;

	//Constructor
	public Player() {
		// Load textures
		//Sets up Player texture array for all the file textures
		playerTex = new ImageIcon[Options.Player_texture_filenames.length];

		//Goes through every file texture
		for (int x = 0; x < playerTex.length; x++) {
			//Creates an Image Icon of the texture and stores it
			ImageIcon temp = new ImageIcon(Options.Player_texture_filenames[x]);
			//Creates a dimension variable for the dimensions of the specific texture
			Dimension d = Options.Player_texture_dimensions[x];

			//Creates a new instance of the Image Icon that is scaled to the specified dimensions
			//and placed into the Texture array
			playerTex[x] = new ImageIcon(temp.getImage().getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT));
		}

		//Sets the ground position to the predefined value
		groundpos = Options.player_VerticalGroundPosition_inPanel;
	}

	//Sets up the player object labels and other settings
	public void setupPlayer() {
		
		//Creates the label 
		PlayerLabel = new JLabel();
		//Sets the Icon of the Label to the standing texture
		setPlayerIcon(0);
		
		//Sets the vertical position of the player to the ground
		ypos = Options.player_VerticalGroundPosition_inPanel;

		//Adds the Label to the panel
		Options.panel_game.add(PlayerLabel);
	}

	//Places the player at a specified position
	public void putPlayerAtLocation(int x) {
		//Sets the location of the label at the y-position
		//based on the bottom of the texture
		//Formula of  position
		//ypos - Height
		PlayerLabel.setLocation(Options.player_horizontalPosition_inPanel, x - PlayerLabel.getHeight());
	}

	//Sets the Player texture to the texture in the ID
	public void setPlayerIcon(int id) {
		//Sets the selected texture to the id
		currentTex = id;
		
		//Creates dimension object for id's texture
		Dimension d = Options.Player_texture_dimensions[id];
		//Resizes the label for the specified texture
		PlayerLabel.setSize(d.width, d.height);
		//Sets the texture to the specified ID
		PlayerLabel.setIcon(playerTex[id]);
		
		//Places the player on the ground
		putPlayerAtLocation(groundpos);
	}

	//Called When the player wants to be updated
	public void update() {
		//Updates the controls of the player
		updateControl();
		//Updates the Physics of the player
		updateMovement();
		//update score by getting the time
		//(Every half second is 1 point)
		Options.score = (System.currentTimeMillis() - startTime)/500;
		//Update the score label
		Options.score_label.setText(Options.Game_Header_ScorePrefix + Options.score);
	}

	//Called when the player controls needs to be updated
	public void updateControl() {
		
		//Tests if the player is on the ground
		//Below or at ground level
		if (ypos >= groundpos) {
			
			//If the duck button is being pressed AND the duck texture
			//isnt currently displayed
			if (Options.duckPressed && currentTex != 1) {
				//Changes the texture
				setPlayerIcon(1);
			//If the duck button isnt being pressed AND the duck texture
			//is currently used
			} else if (!Options.duckPressed && currentTex != 0) {
				//Changes the texture
				setPlayerIcon(0);
			}
			
			//If the duck button isnt pressed and the jump button
			//is pressed, then let the player jump
			if (!Options.duckPressed && Options.jumpPressed) {
				//Set the current velocity of the player to up
				yvel = Options.player_jump_power;
			}
		}
		//No matter what, set jump to false since holding the jump
		//button is not valid
		Options.jumpPressed = false;
	}

	//Updates the movement Physics
	public void updateMovement() {

		//Checks if the player is not on the ground
		//OR if the velocity isnt less than 0
		if (ypos < groundpos || yvel > 0) {
			
			//Move the player Up according to the
			//Velocity variable
			ypos -= yvel;
			//Reduce the velocity by the gravity variable
			yvel -= Options.player_gravity;

			//Sets the player location at its new location
			putPlayerAtLocation((int) ypos);
		} else {
			//currentTex the velocity is either negative and is
			//on the ground
			//Sets the velocity to 0
			yvel = 0;
			//Keeps player on the ground
			putPlayerAtLocation(groundpos);
			ypos = groundpos;
		}
	}
	
	//Called when the collision needs to be tested
	public void testCollision() {
		//check the first 2 items in the obstacle array
		//Is there at least 1 item in the array?
		if(Obstacle.obstacles.size() >= 1) {
			//Test the collision of the first obstacle
			testCollision(Obstacle.obstacles.getFirst());
		}
		//Is there at least 2 items in the array?
		if(Obstacle.obstacles.size() >= 2) {
			//Test the collision of the 2nd obstacle
			testCollision(Obstacle.obstacles.get(1));
		}
	}
	
	//Tests the collision betwee the player and a Obstacle
	//By checking if their Rectangles intersects using the
	//Rectangle class
	public void testCollision(Obstacle o) {
		//Create a Rectangle Object for both the player and obstacle
		Rectangle pr = new Rectangle(PlayerLabel.getLocation(), PlayerLabel.getSize());
		Rectangle or = new Rectangle(o.l.getLocation(), o.l.getSize());
		
		//Does the rectangles intersect
		if(pr.intersects(or)) {
			//They do intersect
			//End the game
			Main.win.gameLostAction();
		}
	}
}