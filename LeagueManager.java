import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.TeamsCollection;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Selections;

public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    // Your code here!
    TeamsCollection teamsCollection = new TeamsCollection();
    Selections selections = new Selections(teamsCollection);
    selections.run();
  }

}
