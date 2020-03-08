import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * LeaderBoardManager Class
 * This class is the managing class for everything related
 * to the leaderboard file and list
 * The Leaderboard file is TopScores.txt and is created
 * and read from the same location the program is
 */
public class LeaderBoardManager {

	//The file object for the TopScores file
	static File file;
	//List of entries in the LeaderBoard
	static ArrayList<LeaderBoardEntry> entries;
	//Boolean which is toggled when the Leaderboard file cannot be
	//read or created
	//when false, LEaderboard is effectively disabled
	static boolean validFile = true;

	//Called to preload the file object
	//Creates one if it doesnt exist
	public static void setup() {
		
		//Creates File object
		file = new File(Options.leaderBoardFile);
		//Initializes Arraylist object
		entries = new ArrayList<LeaderBoardEntry>();
		
		try {
			
			//Does the File exist?
			if (!file.exists()) {
				//if not create a new file
				file.createNewFile();
			}
			
			//Is there a problem to reading or writing to the file?
			if(!file.canRead() || !file.canWrite()) {
				//there is a problem, throw an Exception
				throw new IOException("Cannot read/write file");
			}
			//File is completely fine
			
			//Start a Buffered Reader object
			BufferedReader fReader = new BufferedReader(new FileReader(file));
			
			//Read the first line
			String line = fReader.readLine();
			
			//Loops until all lines are read
			while(line != null) {
				
				//Split the String by the comma, between the name and score
				String[] data = line.split(",");
				
				//Convert the score as a String into a int
				int i = Integer.parseInt(data[1]);
				
				//Add the entry to the list
				entries.add(new LeaderBoardEntry(data[0], i));
				
				//read the next line
				line = fReader.readLine();
			}
			
			//Close the buffered reader
			fReader.close();
		} catch (Exception e) {
			//When any error occurs, the following is done
			
			//Display a Message Dialog saying something is wrong, and Leaderboard will be disabled
			JOptionPane.showMessageDialog(Main.win.frame,
					"Warning: Location of program does not allow for file read/write. Leaderboard will not work", "Warning",
					JOptionPane.WARNING_MESSAGE);
			
			//print the stacktrace to console for debugging
			e.printStackTrace();
			
			//Set valid file to false to disable the leaderboard
			validFile = false;
		}

	}

	//getHighScorerank() returns the rank index in which the score would
	//belong to if added to the leaderboard
	//returns -1 if the score doesnt make the leaderboard
	public static int getHighScoreRank(int score) {
		
		//Checks if Leaderboard is enabled
		if(validFile) {
			
			//Goes through every entry in the list
			for(int x = 0; x < entries.size(); x++) {
				//Is the new score greater than or equal to the current score?
				if(score >= entries.get(x).score) {
					//Return the current index
					return x;
				}
			}
			//The score is not greater than any previous score
			
			//Does the leaderboard even have 10 people on it?
			//If leaderboard doesnt have 10 people on it, the rank
			//of the current score is at the bottom
			if(entries.size() < Options.lead_max_scores) {
				//return the end of the list
				return entries.size();
			} else {
				//if the leaderboard already has 10 people
				//return -1
				return -1;
			}
		} else {
			//the leaderboard is disabled, return -1
			return -1;
		}
	}

	//insertScoreHighScore method
	//Adds the highscore to the list and updates the TopScores file
	public static void insertScoreHighScore(String name, int score, int rank) {
		
		//Is the leaderboard enabled?
		if(validFile) {
			//it is enabled
			
			entries.add(rank, new LeaderBoardEntry(name, score));
			if(entries.size() > Options.lead_max_scores) {
				entries.remove(entries.size() - 1);
			}
			
			//Update file
			try {
				
				//rewrite the entire file
				//Create file writer objects
				FileWriter fwrite = new FileWriter(file, false);
				BufferedWriter writer = new BufferedWriter(fwrite);
				
				//Goes through every object in the Entry array
				for(int x = 0; x < entries.size(); x++) {
					//writes the name and score in the following format
					//Name,score
					writer.write(entries.get(x).name);
					writer.write(",");
					writer.write(String.valueOf(entries.get(x).score));
					writer.write("\n");
				}
				
				//closes the writers to close the file
				writer.close();
				fwrite.close();
				
			} catch (Exception e) {
				//Code run when something goes wrong
				//If something goes wrong, then assume the file is bad
				//and disable the entire leaderboard
				//Tell the user something went wrong
				JOptionPane.showMessageDialog(Main.win.frame,
						"Warning: Something went wrong with file. Is location read/writable? Leaderboard will not work", "Warning",
						JOptionPane.WARNING_MESSAGE);
				//Disable the leaderboard
				validFile = false;
			}
		}
	}

	//displayBoard method
	//When the Leaderboard state is called, this method will fill the
	//name and score panels with the appropiate information
	public static void displayBoard() {
		//is the leaderboard enabled?
		if(validFile) {
			//Create font object for the labels 
			//since it is the same for all entries
			Font f = new Font(Options.leader_Name_font, Font.BOLD, Options.leader_Name_font_size);
			
			//Goes through every entry in the array
			for(int x = 0; x < entries.size(); x++) {
				
				//Gets the Entry object from the array
				LeaderBoardEntry e = entries.get(x);
				
				//Sets up the label that holds the name
				//which is left aligned
				//The format is as follows:
				//RankNum. Name:
				JLabel n = new JLabel((x + 1) + ". " + e.name + ":");
				n.setForeground(Options.leader_text_color);
				n.setHorizontalAlignment(JLabel.LEFT);
				n.setFont(f);
				
				//Sets up the label that holds the score
				//which is right aligned
				JLabel s = new JLabel(String.valueOf(e.score));
				s.setForeground(Options.leader_text_color);
				s.setHorizontalAlignment(JLabel.RIGHT);
				s.setFont(f);
				
				//Adds each object to their associated panel
				Options.LeaderBoard_Name.add(n);
				Options.LeaderBoard_Score.add(s);
			}
		} else {
			//The Leaderboard is disabled
			
			//Tell user that the leaderboard is disabled with the 
			//predefined message
			JLabel l = new JLabel(Options.leader_onFail);
			//adds to panel
			Options.LeaderBoard_Name.add(l);
		}
	}

}
