import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/*
 * The Window class
 * This class manages all JComponents
 * The window class has 1 main JFrame which exists
 * throughout the lifetime of the program instance.
 * The main ContentPane is what changes in order to 
 * switch between menus and screens. This is done
 * with the WindowState class (see below). On 
 * construction, all windowstates are preloaded and
 * constructed and are waiting to be called.
 */
public class Window {

	//The Avaliable window states to the program
	//index 0 is the main menu, index 1 is the Game itself, index 2 is the game over screen
	public WindowState[] states = { new MenuState(), new GameState(), new GameOverState(), new LeaderBoardState() };
	
	//The state that the window is currently in
	public int currentState = 0;

	//The Main Frame that is used in the program instance
	JFrame frame;
	
	//The keylistener instance
	keyLisn kl;
	
	//If the window is currently loading a window State onto the frame
	//This prevents the Scheduled task from running so the game doesnt 
	//run while the window is being repainted
	public boolean isLoading = false;

	//Constructor object
	public Window() {

		// sets up all states/panels ahead of time
		// Goes through all defined states
		for (int x = 0; x < states.length; x++) {
			// Calls setup on the state
			states[x].setup();
		}
		
		//Starts and constructs new KeyListener Instance
		kl = new keyLisn();
	} // end of constructor

	//Method to create and display the JFrame
	public void createAndDisplayFrame() {
		//Creates JFrame instance with predefined title
		frame = new JFrame(Options.Window_Title);
		//Sets up the size of the JFrame with predefined dimensions
		frame.setSize(Options.Window_Size_x, Options.Window_Size_Y);
		//Sets close operation to none (For the confirm window, see next step)
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Adds new Window Listener instance (see below for class)
		frame.addWindowListener(new WinLisn());
		
		// place window to center of screen//
		// gets dimensions of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// set location to center of screen
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		
		//Disallow user to resize the window
		frame.setResizable(false);
		//Sets the Window State to 0, Main menu
		setState(0);
		//Show screen to user
		frame.setVisible(true);
	} // end of createAndDisplayFrame()

	//Method to change Window State
	public void setState(int id) {
		//Sets loading state
		isLoading = true;
		//Change the state variable
		currentState = id;
		//Reset the state to change into
		states[id].reset();
		//setup state to be displayed
		states[id].display(frame);
		//Re-render the entire frame
		redraw();
		//Sets loading state to not loading
		isLoading = false;
	} // end of setState()

	//Method called when Window attempts to close
	public void quitAction() {
		// When the close button is clicked, ask to confirm if they really want to quit
		int result = JOptionPane.showConfirmDialog(frame, Options.Pane_Leave_message, Options.Pane_Leave_title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		// if they say yes, stop the program
		if (result == 0) {
			// close program
			System.exit(0);
		}
	}
	
	//Calls when the player loses the game
	public void gameLostAction() {
		//Set the game state to not running
		Options.gameRunning = false;
		//Waits to
		//Delay the Game over screen
		try {
			TimeUnit.MILLISECONDS.sleep(Options.Over_show_delay_ms);
		} catch (Exception e) {
			System.out.println("Wait failed");
		}
		//Change to Game over screen
		setState(2);
	}
	
	//Method to re-render/draw/paint the window
	public void redraw() {
		//revalidate() recalculates all component
		//positions if a layout manager is used
		frame.revalidate();
		//repaint() clears the JFrame and redraws 
		//all components
		frame.repaint();
	}

	/////////////////////////////////////////////////
	// Window States Superclass at bottom
	
	//Window state for Main menu
	public class MenuState extends WindowState {

		//The main Panel for menu
		JPanel mainPanel;
		//Labels for the panel
		JLabel Title, padding_top, padding_bottom;
		//All button choices for the menu
		JButton Play, Quit, Leader;

		//Preloads the Screen Components
		@Override
		public void setup() {

			//Setup the main Panel with a GridBag layout 
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.setBackground(Options.Main_Background_color);
			
			//Adds padding to the first row
			padding_top = new JLabel();
			addComponentToPanelGridLayout(mainPanel, padding_top, 1, 0, 1, Options.Main_gridTopPaddingSlots, GridBagConstraints.CENTER);

			//Sets up Title with prechosen parameters
			Title = new JLabel(Options.Main_Title);
			Title.setHorizontalAlignment(JLabel.CENTER);
			Title.setFont(new Font(Options.Main_title_font, Font.BOLD, Options.Main_title_font_Size));
			Dimension t_size = new Dimension(Options.Main_Title_width, Options.Main_Title_height);
			Title.setPreferredSize(t_size);
			Title.setMinimumSize(t_size);
			Title.setMaximumSize(t_size);
			addComponentToPanelGridLayout(mainPanel, Title, 1, 1, 1, 1, GridBagConstraints.CENTER);

			//Sets up common items in all buttons
			Font button_font = new Font(Options.Main_button_text_font, Font.BOLD, Options.Main_button_text_font_size);
			Border button_border = BorderFactory.createLineBorder(Options.Main_button_border_color, Options.Main_button_border_thickness, true);
			
			//Sets up the play button (Opens state at index 1)
			Play = new JButton(Options.Main_Play_text);
			Dimension p_size = new Dimension(Options.Main_Button_width, Options.Main_Button_height);
			Play.setPreferredSize(p_size);
			Play.setMinimumSize(p_size);
			Play.setMaximumSize(p_size);
			Play.setFont(button_font);
			Play.setBorder(button_border);
			Play.setBackground(Options.Main_Button_color);
			Play.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setState(1);
				}
			});
			addComponentToPanelGridLayout(mainPanel, Play, 1, 2, 1, 1, GridBagConstraints.CENTER);
			
			//Sets up the Leaderboard Button (Opens state at index 2)
			Leader = new JButton(Options.Main_leader_text);
			Dimension l_size = new Dimension(Options.Over_Button_width, Options.Over_Button_height);
			Leader.setPreferredSize(l_size);
			Leader.setMinimumSize(l_size);
			Leader.setMaximumSize(l_size);
			Leader.setFont(button_font);
			Leader.setBorder(button_border);
			Leader.setBackground(Options.Main_Button_color);
			Leader.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setState(3);
				}
			});
			addComponentToPanelGridLayout(mainPanel, Leader, 1, 3, 1, 1, GridBagConstraints.CENTER);

			//Sets up the Quit button (Attempts to close the program)
			Quit = new JButton(Options.Main_Quit_text);
			Dimension q_size = new Dimension(Options.Main_Button_width, Options.Main_Button_height);
			Quit.setPreferredSize(q_size);
			Quit.setMinimumSize(q_size);
			Quit.setMaximumSize(q_size);
			Quit.setFont(button_font);
			Quit.setBorder(button_border);
			Quit.setBackground(Options.Main_Button_color);
			Quit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					quitAction();
				}
			});
			addComponentToPanelGridLayout(mainPanel, Quit, 1, 4, 1, 1, GridBagConstraints.CENTER);

			padding_bottom = new JLabel();
			addComponentToPanelGridLayout(mainPanel, padding_bottom, 1, 5, 1, Options.Main_gridBottomPaddingSlots, GridBagConstraints.CENTER);

		}

		//Shows the menu on frame
		@Override
		public void display(JFrame frame) {
			frame.setContentPane(mainPanel);
		}

		//Resets the Menu components
		@Override
		public void reset() {
			// nothing to reset
			// do nothing
		}

	} // end of MenuState class
	
	//Window State for the Game Screen
	public class GameState extends WindowState {

		//All Panels for the State
		//The main panel is setup in the following way\
		//------------
		//Header Panel
		//Game Panel
		//Footer Panel
		//------------
		JPanel mainPanel, HeaderPanel, FooterPanel, GamePanel;
		
		//Header Panel Components
		//Labels for Header Components
		JLabel Score, HeaderTitle;
		
		//Footer Panel Components
		//Label for Footer component instructions
		JLabel text_Intructions_Left, text_Intructions_Right;
		
		@Override
		public void setup() {
			
			//Sets up Header panel//
			HeaderPanel = new JPanel();
			HeaderPanel.setLayout(new GridLayout(1, 2));
			HeaderPanel.setMaximumSize(new Dimension(Options.Window_Size_x, Options.Game_Header_Height));
			HeaderPanel.setMinimumSize(new Dimension(Options.Window_Size_x, Options.Game_Header_Height));
			HeaderPanel.setPreferredSize(new Dimension(Options.Window_Size_x, Options.Game_Header_Height));
			
			//Creates font object used by all JLabels
			Font f = new Font(Options.Game_Font, Font.BOLD, Options.Game_Font_Size_Header);
			
			//Sets up the Header Title Label
			HeaderTitle = new JLabel(Options.Game_Header_Title);
			HeaderTitle.setHorizontalAlignment(JLabel.CENTER);
			HeaderTitle.setFont(f);
			HeaderPanel.add(HeaderTitle);
			
			//Sets up the Score in Header label
			Score = new JLabel(Options.Game_Header_ScorePrefix);
			Score.setHorizontalAlignment(JLabel.CENTER);
			Score.setFont(f);
			HeaderPanel.add(Score);
			
			//recreates Font object for footer components
			f = new Font(Options.Game_Font, Font.BOLD, Options.Game_Font_Size_Footer);
			
			//Sets up Footer panel//
			FooterPanel = new JPanel();
			FooterPanel.setLayout(new GridLayout(1, 2));
			FooterPanel.setMaximumSize(new Dimension(Options.Window_Size_x, Options.Game_Footer_Height));
			FooterPanel.setMinimumSize(new Dimension(Options.Window_Size_x, Options.Game_Footer_Height));
			FooterPanel.setPreferredSize(new Dimension(Options.Window_Size_x, Options.Game_Footer_Height));
			
			//Sets up the left instruction text label
			text_Intructions_Left = new JLabel(Options.Game_Footer_Text_Left);
			text_Intructions_Left.setHorizontalAlignment(JLabel.CENTER);
			text_Intructions_Left.setFont(f);
			FooterPanel.add(text_Intructions_Left);
			
			//Sets up the right instruction text label
			text_Intructions_Right = new JLabel(Options.Game_Footer_Text_Right);
			text_Intructions_Right.setHorizontalAlignment(JLabel.CENTER);
			text_Intructions_Right.setFont(f);
			FooterPanel.add(text_Intructions_Right);
			
			//Sets up Game Panel//
			GamePanel = new JPanel();
			GamePanel.setLayout(null);
			GamePanel.setBackground(Options.Game_background_color);
			
			Options.panel_game = GamePanel;
					
			//Setup main panel and add panels//
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(HeaderPanel, BorderLayout.NORTH);
			mainPanel.add(GamePanel, BorderLayout.CENTER);
			mainPanel.add(FooterPanel, BorderLayout.SOUTH);
			
		}

		//Shows the screen on screen
		@Override
		public void display(JFrame frame) {
			//sets global score object
			Options.score_label = Score;
			//Sets the content pane
			frame.setContentPane(mainPanel);
			//sets up player Object
			Options.player = new Player();
			Options.player.setupPlayer();
			//Adds key listener and causes key focus
			frame.addKeyListener(kl);
			frame.requestFocusInWindow();
			//Starts up obstacle objects
			Obstacle.StartupObstacles();
			//Sets game state to running
			Options.gameRunning = true;
			//Sets start time to current time for score
			Options.player.startTime = System.currentTimeMillis();
		}

		//resets objects in screen
		@Override
		public void reset() {
			//Resets Score text
			Score.setText(Options.Game_Header_ScorePrefix);
			
			//Resets the main panel
			mainPanel.remove(GamePanel);
			GamePanel = new JPanel();
			GamePanel.setLayout(null);
			GamePanel.setBackground(Options.Game_background_color);
			Options.panel_game = GamePanel;
			mainPanel.add(GamePanel, BorderLayout.CENTER);
			
		}
		
	}
	
	//Game Over screen state
	public class GameOverState extends WindowState {

		//Main Panel
		JPanel mainPanel;
		//Labels for the panel
		JLabel Title, Score, padding_top, padding_bottom;
		//Options for the Over Screen
		JButton Continue, Quit, Leader;
		
		//Sets up the state for preloading
		@Override
		public void setup() {
			
			//Sets up main panel
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.setBackground(Options.Over_Background_color);

			//Adds padding in grid
			padding_top = new JLabel();
			addComponentToPanelGridLayout(mainPanel, padding_top, 1, 0, 1, Options.Over_gridTopPaddingSlots, GridBagConstraints.CENTER);

			//Sets up the title JLabel
			Title = new JLabel(Options.Over_Title);
			Title.setHorizontalAlignment(JLabel.CENTER);
			Dimension t_size = new Dimension(Options.Over_Title_width, Options.Over_Title_height);
			Title.setPreferredSize(t_size);
			Title.setMinimumSize(t_size);
			Title.setMaximumSize(t_size);
			Title.setFont(new Font(Options.Over_Title_font, Font.BOLD, Options.Over_title_font_size));
			Title.setForeground(Options.Over_title_text_color);
			addComponentToPanelGridLayout(mainPanel, Title, 1, 1, 1, 1, GridBagConstraints.CENTER);
			
			//Sets up the Score JLabel
			Score = new JLabel(Options.Over_ScorePrefix);
			Score.setHorizontalAlignment(JLabel.CENTER);
			Dimension s_size = new Dimension(Options.Over_Score_width, Options.Over_Score_height);
			Score.setPreferredSize(s_size);
			Score.setMinimumSize(s_size);
			Score.setMaximumSize(s_size);
			Score.setFont(new Font(Options.Over_score_font, Font.BOLD, Options.Over_score_font_size));
			Score.setForeground(Options.Over_score_text_color);
			addComponentToPanelGridLayout(mainPanel, Score, 1, 2, 1, 1, GridBagConstraints.CENTER);

			//Creates the Font and Border objects for all buttons
			Font f = new Font(Options.Over_button_font, Font.BOLD, Options.Over_button_font_size);
			Border b = BorderFactory.createLineBorder(Options.Over_button_border_color, Options.Over_button_border_thickness, true);
			
			//Creates Continue button object (Opens state 1)
			Continue = new JButton(Options.Over_Play_text);
			Dimension p_size = new Dimension(Options.Over_Button_width, Options.Over_Button_height);
			Continue.setPreferredSize(p_size);
			Continue.setMinimumSize(p_size);
			Continue.setMaximumSize(p_size);
			Continue.setBackground(Options.Over_button_back_color);
			Continue.setFont(f);
			Continue.setForeground(Options.Over_button_text_color);
			Continue.setBorder(b);
			Continue.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setState(1);
				}
			});
			addComponentToPanelGridLayout(mainPanel, Continue, 1, 3, 1, 1, GridBagConstraints.CENTER);
			
			//creates Leader button object (Opens state 3)
			Leader = new JButton(Options.Over_Leader_text);
			Dimension l_size = new Dimension(Options.Over_Button_width, Options.Over_Button_height);
			Leader.setPreferredSize(l_size);
			Leader.setMinimumSize(l_size);
			Leader.setMaximumSize(l_size);
			Leader.setBackground(Options.Over_button_back_color);
			Leader.setFont(f);
			Leader.setForeground(Options.Over_button_text_color);
			Leader.setBorder(b);
			Leader.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setState(3);
				}
			});
			addComponentToPanelGridLayout(mainPanel, Leader, 1, 4, 1, 1, GridBagConstraints.CENTER);

			//creates Leader button object (Attempts to exit program)
			Quit = new JButton(Options.Over_Quit_text);
			Dimension q_size = new Dimension(Options.Over_Button_width, Options.Over_Button_height);
			Quit.setPreferredSize(q_size);
			Quit.setMinimumSize(q_size);
			Quit.setMaximumSize(q_size);
			Quit.setBackground(Options.Over_button_back_color);
			Quit.setFont(f);
			Quit.setForeground(Options.Over_button_text_color);
			Quit.setBorder(b);
			Quit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					quitAction();
				}
			});
			addComponentToPanelGridLayout(mainPanel, Quit, 1, 5, 1, 1, GridBagConstraints.CENTER);

			//Sets up bottom padding
			padding_bottom = new JLabel();
			addComponentToPanelGridLayout(mainPanel, padding_bottom, 1, 6, 1, Options.Main_gridBottomPaddingSlots, GridBagConstraints.CENTER);
		}

		//Shows the window state on frame
		@Override
		public void display(JFrame frame) {
			//sets the text to the score label to the prefix and score
			Score.setText(Options.Over_ScorePrefix + Options.score);
			
			//sets the content pane
			frame.setContentPane(mainPanel);
			
			//Checks for highscore
			//Get the rank of the highscore obtained
			//returns -1 when not on the leaderboard
			int rank = LeaderBoardManager.getHighScoreRank((int)Options.score);
			//Is the score on the leaderboard?
			if(rank != -1) {
				//Get the returned name
				String name = JOptionPane.showInputDialog(frame, "NEW HIGHSCORE! Rank: " + (rank + 1) + " Input your name", 
						"HIGHSCORE", JOptionPane.INFORMATION_MESSAGE);
				
				//Did they enter no name?
				if(name == null) {
					//Set a default name
					name = "No Name";
				}
				//removes all commas from the name
				//for formatting reasons
				name.replace(',', ' ');
				
				//Saves the highscore into the leaderboard file
				LeaderBoardManager.insertScoreHighScore(name, (int)Options.score, rank);
			}
		}

		//Resets the Panel
		@Override
		public void reset() {
			//Reset only the score label to only the prefix
			Score.setText(Options.Over_ScorePrefix);
		}
	}
	
	//Leaderboard screen state
	public class LeaderBoardState extends WindowState {

		//The panels for the state
		//The main panel is arranged in this way
		//Title
		//Board
		//	LeftBoard RightBoard
		//back button
		JPanel mainPanel, Board, Board_L, Board_R;
		//The title at the top of the screen
		JLabel label_Head_Title;
		//The Main menu button
		JButton button_back;
		
		//Setups the state for preloading
		@Override
		public void setup() {
			//Setup for main panel
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.setBackground(Options.leader_background_color);
			
			//Adds the Header Title
			label_Head_Title = new JLabel(Options.lead_Title);
			label_Head_Title.setHorizontalAlignment(JLabel.CENTER);
			label_Head_Title.setVerticalAlignment(JLabel.CENTER);
			label_Head_Title.setPreferredSize(Options.lead_back_title_dim);
			label_Head_Title.setMinimumSize(Options.lead_back_title_dim);
			label_Head_Title.setMaximumSize(Options.lead_back_title_dim);
			label_Head_Title.setFont(new Font(Options.leader_Title_font, Font.BOLD, Options.leader_title_font_size));
			label_Head_Title.setForeground(Options.leader_text_color);
			addComponentToPanelGridLayout(mainPanel, label_Head_Title, 1, 0, 2, 1, GridBagConstraints.CENTER);
			
			//sets up the Board Panel inside the main panel
			Board = new JPanel();
			Board.setLayout(new GridLayout(1,2));
			Board.setPreferredSize(Options.lead_back_panel_dim);
			Board.setMinimumSize(Options.lead_back_panel_dim);
			Board.setMaximumSize(Options.lead_back_panel_dim);
			Board.setBackground(Options.leader_background_color);
			addComponentToPanelGridLayout(mainPanel, Board, 0, 1, 4, 5, GridBagConstraints.CENTER);
			
			//sets up the Left Board panel inside the Board panel
			Board_L = new JPanel();
			Board_L.setBackground(Options.leader_background_color);
			Board_L.setLayout(new GridLayout(Options.lead_max_scores,1));
			
			//sets up the Right Board panel inside the Board panel
			Board_R = new JPanel();
			Board_R.setBackground(Options.leader_background_color);
			Board_R.setLayout(new GridLayout(Options.lead_max_scores,1));
			
			//adds both boards to the Board panel
			Board.add(Board_L);
			Board.add(Board_R);
			
			//adds the back button to the Main panel (sends to state 0)
			button_back = new JButton(Options.leader_back_button_label);
			button_back.setPreferredSize(Options.lead_back_button_dim);
			button_back.setMinimumSize(Options.lead_back_button_dim);
			button_back.setMaximumSize(Options.lead_back_button_dim);
			button_back.setBackground(Options.leader_button_background_color);
			button_back.setForeground(Options.leader_button_text_color);
			button_back.setFont(new Font(Options.leader_button_text_font, Font.BOLD, Options.leader_button_text_size));
			button_back.setBorder(BorderFactory.createLineBorder(Options.leader_button_border_color, 
									Options.leader_button_border_thickness, true));
			button_back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setState(0);
				}
			});
			addComponentToPanelGridLayout(mainPanel, button_back, 1, 6, 2, 1, GridBagConstraints.CENTER);
		}

		//Shows the state on screen
		@Override
		public void display(JFrame frame) {
			Options.LeaderBoard_Name = Board_L;
			Options.LeaderBoard_Score = Board_R;
			LeaderBoardManager.displayBoard();
			frame.setContentPane(mainPanel);
		}

		//resets the state
		@Override
		public void reset() {
			//Remove the left and right boards
			Board.remove(Board_L);
			Board.remove(Board_R);
			
			//Recreates the left board
			Board_L = new JPanel();
			Board_L.setBackground(Options.leader_background_color);
			Board_L.setLayout(new GridLayout(Options.lead_max_scores,1));
			
			//Recreates the right board
			Board_R = new JPanel();
			Board_R.setBackground(Options.leader_background_color);
			Board_R.setLayout(new GridLayout(Options.lead_max_scores,1));
			
			//adds them back to the board
			Board.add(Board_L);
			Board.add(Board_R);
		}
		
	}

	/*
	 * A Window state is what the Window is showing, for instance is the window
	 * showing a menu, or gameplay Window states contain the code for creating a
	 * panel and its components
	 * 
	 * Using Window States makes changing between panels more efficient and easier
	 */
	public abstract class WindowState {
		
		//Setup is called when the program starts and this calls to preload objects
		public abstract void setup();

		//Display called when program wants to display the state on the frame
		public abstract void display(JFrame frame);

		//Reset called when the program wants to clear all data from the state 
		public abstract void reset();
		
		//method for doing GridBagConstraints on JComponent for adding to a Panel with a GridBagLayout
		public void addComponentToPanelGridLayout(JPanel panel, JComponent comp, int x, int y, int width, int height, int alignment) {
			//Creates GridBadConstraints with all parameters
			GridBagConstraints g = new GridBagConstraints();
			g.gridx = x;
			g.gridy = y;
			g.gridwidth = width;
			g.gridheight = height;
			g.insets = new Insets(Options.Main_gridPadding, Options.Main_gridPadding, Options.Main_gridPadding, Options.Main_gridPadding);
			g.anchor = alignment;
			g.weightx = 100;
			g.weighty = 100;
			g.fill = GridBagConstraints.NONE;

			//Adds to panel
			panel.add(comp, g);
		}

	}
	
	/////////////////
	//Listener classes
	
	//Window Listener for window actions
	//Used for when User attempts to close the window
	//It sends a verify message to test if user really 
	//wants to quit, and prompts the quit
	public class WinLisn implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			//When the window is being attempted to close,
			//Run quitAction();
			quitAction();
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}
	}
	
	//Key listener
	//Tests if a key is pressed and does methods
	//Used to test if player pressed the W (Jump) or S (crouch) button
	//And do the action accordingly
	public class keyLisn implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}
		
		//When the Duck button is released, tell all classes that it is released
		@Override
		public void keyReleased(KeyEvent e) {
			//Is the player currently ducking, AND did the play just release the duck button
			if(Options.duckPressed && e.getKeyCode() == Options.player_button_duck) {
				//tell all classes to stop ducking
				Options.duckPressed = false;
			} 
		}
		
		//When any key is pressed, this method is called
		//Tells all classes when jumping or ducking
		@Override
		public void keyPressed(KeyEvent e) {
			//has the player not jumped, and is the player trying to jump
			if(!Options.jumpPressed && e.getKeyCode() == Options.player_button_jump) {
				//Tell classes to jump
				Options.jumpPressed = true;
			//has the player not ducked and is trying to duck
			} else if(!Options.duckPressed && e.getKeyCode() == Options.player_button_duck){
				//Tell classes to duck
				Options.duckPressed = true;
			}
		}
	}

}
