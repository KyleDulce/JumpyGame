import java.awt.Dimension;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * The Obstacle class
 * Manages all moving obstacles on screen
 */
public class Obstacle {
	
	//List of all obstacles in order of spawn on screen
	public static LinkedList<Obstacle> obstacles;
	
	//All possible textures of the obstacles
	//See Options Class for more infomation
	static ImageIcon[][] textures;
	
	//The current speed of all Obstacles
	static double curSpeed, 
	//The time in miliseconds before the next spawn
	timeToNextSpawn = 0;
	
	//The time between each frame
	static int timeBetweenUpdates;
	
	//The Game panel that is currently being used
	static JPanel gp;
	//The random generator
	static Random rnd;
	
	//Called when Obstacle class should be started
	public static void StartupObstacles() {
		
		//Sets up Obstacle list
		obstacles = new LinkedList<Obstacle>();
		//Set the list globally to other classes
		Options.obstacles = obstacles;
		//Sets the speed to predefined speed
		curSpeed = Options.obstacle_spawn_start_speed;
		//Sets the spawn delay to the start delay
		timeToNextSpawn = Options.obstacle_spawn_start_delay;
		//Sets the time between updates to the framerate interval
		timeBetweenUpdates = 1000/Options.framerate;
		//Starts up Random object
		rnd = new Random();
		//gets the game panel
		gp = Options.panel_game;
		
		//preload textures
		//Goes through every texture and rescales the image for the game
		textures = new ImageIcon[Options.obstacle_texture_filenames.length][];
		//Goes through every Row
		for(int r = 0; r < textures.length; r++) {
			//Each row, set up the array inside those arrays
			textures[r] = new ImageIcon[Options.obstacle_texture_filenames[r].length];
			//Sets current dimension for the row to the predefined dimension
			Dimension d = Options.obstacleDims[r];
			
			//Goes through every Col
			for(int c = 0; c < textures[r].length; c++) {
				//Creates Image icon via the filename
				ImageIcon i = new ImageIcon(Options.obstacle_texture_filenames[r][c]);
				//Rescales the image based on the dimensions above
				i = new ImageIcon(i.getImage().getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT));
				//Sets the texture in the array to newly created object
				textures[r][c] = i;
			}
		}
	}
	
	//Called when the obstacle movements are updated
	public static void updateObstacles() {
		
		//Goes through every obstacle
		for(int x = 0; x < obstacles.size(); x++) {
			//Gets the obstacle
			Obstacle o = obstacles.get(x);
			//Moves its xposition based on the current speed
			o.xpos -= curSpeed;
			//Moves the obstacle label to the xposition
			o.setAtLocation((int) o.xpos);
		}
		
		//despawn obstacles off screen
		//Is there at least 1 obstacle?
		if(obstacles.size() > 1) {
			//Get the first obstacle (the one that is the oldest and
			//Furthest left)
			Obstacle o = obstacles.getFirst();
			//Is this obstacle beyong the screen?
			if(o.xpos <= Options.obstacle_despawn_location_x) {
				//Remove from the game panel
				gp.remove(o.l);
				//Remove from the obstacle list
				obstacles.removeFirst();
			}
		}
		
		//Spawn objects
		//Calculate the time to the next spawn via the updates
		timeToNextSpawn -= ((double)timeBetweenUpdates)/1000;
		//Is the time to next spawn less than or equal to 0?
		if(timeToNextSpawn <= 0) {
			//Spawn the obstacle
			spawnObstacle();
			//Increase the speed by the acceleration
			curSpeed += Options.obstacle_spawn_speed_acceleration;
			//Set the new time to next spawn
			timeToNextSpawn = Options.obstacle_spawn_delay;
		}
	}
	
	//Called when spawning an obstacle
	public static void spawnObstacle() {
		//Create the new obstacle object
		Obstacle o = new Obstacle();
		
		//Choose its type (Low, high or flying)
		int type = rnd.nextInt(3);
		//Gets the texture variation of the obstacle
		int variation = rnd.nextInt(textures[type].length);
		
		//Sets the current y position of the obstacle to its defined position
		o.yPos = Options.obstacle_vertical_pos[type];
		//Creates the label for the obstacle
		o.l = new JLabel(textures[type][variation]);
		//Gets the obstacles dimentions
		Dimension d = Options.obstacleDims[type];
		//Sets the size of the label to its dimensions
		o.l.setSize(d.width, d.height);
		
		//Adds the label to the panel
		gp.add(o.l);
		//Sets the location of the obstacle to the spawn location
		o.setAtLocation(Options.obstacle_spawn_location_x);
		
		//Add the obstacle to the list
		obstacles.add(o);
	}
	
	//non-static
	//Obstacle object methods
	int yPos; 		//The current y position of the obstacle
	double xpos;	//The current x position of the obstacle
	JLabel l;		//The Label for the obstacle
	
	//places the label of the osbtacle at a position
	public void setAtLocation(int xpos) {
		//Sets the location of the obstacle while allowing the y position to
		//Define where the bottom of the label goes rather than the top
		l.setLocation(xpos, yPos - l.getHeight());
		//Sets the x position holder to current x pos
		this.xpos = xpos;
	}
	
}
