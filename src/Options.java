import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * This class contains all changable settings for ease of
 * access and finding
 * 
 * Since all design variables are in 1 area, finding these
 * variables and changing their values when needed can go more smoothly
 * 
 * Shared Variables are also found at the bottom. these variables are 
 * for individual window components and/or for physics or general
 * controls
 */
public class Options {
	
	//Window properties
	public static int 
			//The dimensions of the JFrame
			Window_Size_x = 800, 
			Window_Size_Y = 500;
	public static String
			//The Title of the Window
			Window_Title = "Jumpy Game";
	
	//Main Gameloop properties
	//The Frame and update rate of the JFrame
	public static int framerate = 25;
	
	//JOptionPane Message Dialog properties
	//Using the JOption pane, the message displayed when quitting
	public static String Pane_Leave_message = "Are you sure you want to quit?";
	//Using the JOption pane, the title of the window when quitting
	public static String Pane_Leave_title = "Quit?";
	
	//Main Menu Properties
						 //On the Main menu, the title on the screen
	public static String Main_Title = "Jumpy Game",
						 //The label on the play button
						 Main_Play_text = "Play",
						 //The label on the Quit button
						 Main_Quit_text = "Quit",
						 //Label on the Leaderboard button
						 Main_leader_text = "LeaderBoard",
						 //The Font of the text on the Title Label
						 Main_title_font = "Britannic Bold",
						 //The Font of the text on the button
						 Main_button_text_font = "Cooper Black";
	
						//The Background Color of the Main menu
	public static Color Main_Background_color = Color.orange,
						//The Background Color of the Buttons of the Main Menu
						Main_Button_color = new Color(51, 153, 255),
						//The Text Color of the Buttons of the Main Menu
						Main_Button_text_color = Color.white,
						//The Border Color of the Buttons of the Main Menu
						Main_button_border_color = Color.white;
				
	public static int 
				//The font size of the text in the title Label
				Main_title_font_Size = 30,
				//The font size of the text in the buttons
				Main_button_text_font_size = 18,
				//The Border thickness of the buttons
				Main_button_border_thickness = 3,
				//The amount of padding between slots on the grid
				Main_gridPadding = 10,
				//The number of slots on the top and bottom that is taken up as a
				//Large "padding"
				Main_gridTopPaddingSlots = 2,
				Main_gridBottomPaddingSlots = 2,
					  
				//The dimensions of the Title JLabel
				Main_Title_width = 200,
				Main_Title_height = 50,
				//The dimensions of the JButtons in the menu
				Main_Button_width = 200,
				Main_Button_height = 50;
	
	//Game Over Menu Properties
	//The Text on the main JLabel
	public static String Over_Title = "GAME OVER",
				//The Prefix shown for the display of score
				Over_ScorePrefix = "Score: ",
				//The text shown on the continue button
				Over_Play_text = "Continue",
				//The text shown on the Quit button
				Over_Quit_text = "Quit",
				//The text shown on the LeaderBoard button
				Over_Leader_text = "Leaderboard",
				Over_Title_font = "Britannic Bold",
				Over_score_font = "Cooper Black",
				Over_button_font = "Cooper Black";
	
	//The padding between slots of the grid
	public static int Over_gridPadding = 5,
				//The number of slots on the top and bottom that is taken up as a
				//Large "padding"
				Over_gridTopPaddingSlots = 2,
				Over_gridBottomPaddingSlots = 1,
				
				//The dimensions of the JLabel title
				Over_Title_width = 200,
				Over_Title_height = 40,
				//The Dimensions of the JLabel for the score
				Over_Score_width = 300,
				Over_Score_height = 40,
				//The Dimensions of the Buttons in the menu
				Over_Button_width = 200,
				Over_Button_height = 50,
				//The delay when the game ends before the Game Over screen shows in miliseconds
				Over_show_delay_ms = 1500,
				
				//The Font Size of the Title Label in the GameOver Screen
				Over_title_font_size = 30,
				//The Font Size of the Score Label in the GameOver Screen
				Over_score_font_size = 36,
				//The Font Size of the button text in the GameOver Screen
				Over_button_font_size = 20,
				//The border thickness around the buttons in the GameOver Screen
				Over_button_border_thickness = 3;
	
						//The background Color of the GameOver Screen
	public static Color Over_Background_color = new Color(255, 153, 51),
						//The Color of the text in the Title Label
						Over_title_text_color = Color.white,
						//The Color of the text in the Score Label
						Over_score_text_color = Color.white,
						//The Color of the text in the Buttons
						Over_button_text_color = Color.white,
						//The Color of the background of the buttons
						Over_button_back_color = Color.blue,
						//The Color of the border of the buttons
						Over_button_border_color = Color.white;
	
	//Leaderboard Properties
	//The title shown on the Leaderboard
	public static String lead_Title = "Leaderboard",
						//The text on the back button on leaderboard
						leader_back_button_label = "Main Menu",
						//The text on the leaderboard state when the file cannot be read to or written to
						leader_onFail = "Leaderboard not avaliable due to file cannot be read or written",
						//The font of the text of the Title Label
						leader_Title_font = "Britannic Bold",
						//The font of the text of the Name Labels
						leader_Name_font = "Cooper Black",
						//The font of the text of the Score Labels
						leader_button_text_font = "Cooper Black";
	
						//The font size of the Name Labels
	public static int leader_Name_font_size = 20,
						//The font size of the Title Label
						leader_title_font_size = 30,
						//The font size of the Button Labels
						leader_button_text_size = 18,
						//The thickness of the border on the button
						leader_button_border_thickness = 3;
	
						//The background Color of the leaderboard screen
	public static Color leader_background_color = new Color(153, 0, 115),
						//The Text Color of the leaderboard screen
						leader_text_color = new Color(153, 194, 255),
						//The background Color of the buttons on the leaderboard screen
						leader_button_background_color = new Color(26, 26, 255),
						//The text Color of the buttons leaderboard screen
						leader_button_text_color = Color.black,
						//The border Color of the buttons leaderboard screen
						leader_button_border_color = new Color(0, 0, 255);
	
							//The Dimension of the back button
	public static Dimension lead_back_button_dim = new Dimension(200, 60),
							//The Dimension of the Title Label
							lead_back_title_dim = new Dimension(200, 60),
							//The Dimension of the LeaderBoard Panel
							lead_back_panel_dim = new Dimension(400, 350);
					//The max amount of scores in the LeaderBoard
	public static int lead_max_scores = 10;
	
	
	//GamePlay Properties
	//Gameplay screen Header and footer vertical size
	public static int Game_Header_Height = 55,
					Game_Footer_Height = 85,
					//Gameplay Header and footer text font size
					Game_Font_Size_Header = 20,
					Game_Font_Size_Footer = 16;
	
	//The Text on the Title section of the Screen
	public static String Game_Header_Title = "Jump Game",
						//The Text prefix on the Score section of the Screen
						Game_Header_ScorePrefix = "Score/Time: ",
						//The text on the bottom left and right of the screen
						Game_Footer_Text_Left = "Dodge the obstacles so you dont get hit",
						Game_Footer_Text_Right = "Press the W Key to jump and the S Key to Duck",
						//The Font of the text on the screen
						Game_Font = "Helvetica";
	
	public static Color Game_background_color = new Color(0, 204, 204);
	
	//Player Data Properties
	//Player textures file locations
	//Placed in an array so it may be expanded easily when required
	//textures where index 0 is normal texture, index 1 is duck texture
	//index 2 onwards unused
	public static String[] Player_texture_filenames = {
			"PlayerS.png", "PlayerC.png"
	};
	//Each texture file associated dimensions
	public static Dimension[] Player_texture_dimensions = {
			new Dimension(51, 90),
			new Dimension(51, 40)
	};
	
	public static int 
			//The x coordinate in the panel where the player is placed
			player_horizontalPosition_inPanel = 100,
			//The location of "ground" for the player sprite
			player_VerticalGroundPosition_inPanel = 250,
			
			//Control buttons
			//buttons that will control the player in game
			
			//The jump button
			//W key
			player_button_jump = KeyEvent.VK_W,
			//The Duck button
			//S key
			player_button_duck = KeyEvent.VK_S;
	
	//The acceleration of gravity in the game
	//in pixels per tick
	public static double player_gravity = 1.0,
			//The velocity of the player sprite
			//when the player jumps
			player_jump_power = 12.5;
	
	//Obstacle data Properties
	//There is 3 types of obstacles, low obstacles
	//tall obstacles and flying obstacles
	//The delay between spawning of obstacles in seconds
	public static double obstacle_spawn_delay = 2.0,
						//the delay before spawning the first obstacle
						obstacle_spawn_start_delay = 2.5,
						//the starting speed all obstacles will move at
						obstacle_spawn_start_speed = 8.2, 
						//How fast the obstacle speed will accelerate
						obstacle_spawn_speed_acceleration = 0.05;
	
	public static int obstacle_spawn_location_x = 800, 
					obstacle_despawn_location_x = -60;
	
	//The Vertical height of the 3 types of obstacles
	//0=low obstacle 1=tall obstacles, 2=flying obstacle
	public static int[] obstacle_vertical_pos = {
		250, 250, 180	
	};
	
	//The dimensions of the 3 types of obstacles
	//0=low obstacle 1=tall obstacles, 2=flying obstacle
	public static Dimension[] obstacleDims = {
		new Dimension(68, 27),
		new Dimension(53, 47),
		new Dimension(53, 27)
	};
	
	//All the Avaliable textures of the obstacles
	//row 0=low obstacle textures
	//row 1=tall obstacle textures
	//row 2- flying obstacles textures
	//the use of a 2d array allows the textures to have
	//multiple variations and be able to randomly change
	public static String[][] obstacle_texture_filenames = {
			{"brick.png", "Fire.png"},
			{"EiffelTower.png","Spikes.png","Computer.png"},
			{"f-16.png", "bird.png"}
	};
	
	//LeaderBoardManager Properties
	public static String leaderBoardFile = "TopScores.txt";
	
	/////////////////////////////////////
	//Variables that are shared by code//
	/////////////////////////////////////
	
	//Is the game running? variables is true when the minigame
	//is running
	public static boolean gameRunning = false,
						//Is true when the Jump button is pressed
						jumpPressed = false,
						//Is true when the Duck button is pressed
						duckPressed = false;
	
	//The Game panel in the Gamestate window
	public static JPanel panel_game;
	//The Jlabel in the Game panel for the score
	public static JLabel score_label;
	//The score the player has
	public static long score;
	//the list of all Obstacles in the Panel
	public static LinkedList<Obstacle> obstacles;
	//The player object that is currently used
	public static Player player;
	//the LeaderBoard Panels
	public static JPanel LeaderBoard_Name,
						LeaderBoard_Score;
	
}
