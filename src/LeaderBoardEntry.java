
/*
 * An Object for all Entries of the Leaderboard
 */
public class LeaderBoardEntry {
	public String name;	//The name of the player
	public int score;		//The recorded score of the player

	//Constructor for creating a leaderboardentry object
	//With name and score defined
	public LeaderBoardEntry(String name, int score) {
		this.name = name;
		this.score = score;
	}
}
