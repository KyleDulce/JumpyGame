/*
 * JUMPY GAME
 * By Kyle Dulce
 * 
 */

import java.util.Timer;
import java.util.TimerTask;

/*
 * The Main class is only the entry point of the Game and
 * includes the game loop that updates certain states of 
 * the game. See the loop method, Player and Obstacle classes
 * for more infomation.
 */
public class Main {

	//The Game's window object
	//Only 1 window per instance
	//See Window class
	public static Window win;

	// Entry Point main()
	public static void main(String[] args) {
		
		//Window Creation
		//Creates a new window object
		win = new Window();
		//show the window on screen
		win.createAndDisplayFrame();
		
		/*
		 * Sets up the Leader Board manager
		 * The Leader Board Manager manages the leader
		 * board files to save and check for new
		 * highscores
		 */
		LeaderBoardManager.setup();
		
		/* 
		 * Loop startup Define and create a Timer object 
		 * The java.util.Timer class can schedule tasks 
		 * into the computer that can either repeat or 
		 * be called exactly once at an exact time The 
		 * timer object will be used to schedule and 
		 * repeat an update loop that updates the game's 
		 * physics and collision detection
		 */
		Timer m = new Timer();
		
		/* 
		 * Schedule the loop to run the update() Method
		 * the loop will have no start delay and will run
		 * at the predefined rate in the Options class
		 * the delay between updates is calculated using
		 * 1 sec / f/s
		 */
		m.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, 1000 / Options.framerate);
	}

	/*
	 * The Update() Method
	 * runs only if the game is running and will update
	 * the physics, movement and collision detection for
	 * obstacles, and the player. 
	 * The loop also re-renders the Jframe, updating its
	 * visuals
	 */
	public static void update() {
		//Is the window loading a state?
		//If the window is loading a state, Then do not
		//Allow the game to be updated (for sync reasons)
		if(!win.isLoading) {
			//is the Game running?
			if (Options.gameRunning) {
				//Update obstacles and player
				Obstacle.updateObstacles();
				Options.player.update();
				//render Frame
				win.redraw();
				//Tests if the player collided with the object
				Options.player.testCollision();
			}
		}
	}

}
