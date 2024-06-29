package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents a nameable team with a list of players
public class Team {
    List<Player> team; //List of players
    String teamName;    // Name of team


    //EFFECTS: Constructs a team where user inputs the name of the team
    public Team(String name) {
        team = new ArrayList<>();
        this.teamName = name;
    }

    /*
     * REQUIRES: team.size() < 15
     * MODIFIES: this
     * EFFECTS: Adds a player to the list of the selected team,
     */
    public void addplayer(Player p) {
        if (team.size() < 15) {
            team.add(p);
            EventLog.getInstance().logEvent(new Event("Added " + p.getName() + " to " + this.getTeamName()));
        }
    }

    /*
     * REQUIRES: team.size() >= 5
     * EFFECTS: Creates a list of all the starting players in team,
     *          but only once the starting players have been initialized
     */
    public List<Player> getstarters() {
        List<Player> starters = new ArrayList<>();
        if (team.size() >= 5) {
            starters.add(team.get(0));
            starters.add(team.get(1));
            starters.add(team.get(2));
            starters.add(team.get(3));
            starters.add(team.get(4));
        }
        EventLog.getInstance().logEvent(new Event("Viewing " + this.getTeamName() + " starting players"));
        return starters;
    }

    /*
     * REQUIRES: team.size() = 15
     * EFFECTS: Creates a list of all substitute players, but only once the whole team is made
     */
    public List<Player> getsubs() {
        List<Player> subs = new ArrayList<>();
        if (team.size() == 15) {
            for (int i = 5; i < 15; i++) {
                subs.add(team.get(i));
            }
        }
        EventLog.getInstance().logEvent(new Event("Viewing " + this.getTeamName() + " substitute players"));
        return subs;
    }

    //EFFECTS: Returns the team captain player
    public Player getTeamCaptain() {
        Player captain = team.get(0);
        return captain;
    }

    public List<Player> getTeam() {
        return team;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Player getPlayer(int i) {
        return team.get(i);
    }

    //EFFECTS: Converts Team into Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Team name", teamName);
        json.put("Players", playersToJson());
        return json;
    }

    // EFFECTS: returns players in this team as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player p : team) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }
}

