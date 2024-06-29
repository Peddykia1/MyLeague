package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//Represents a League with a list of teams
public class League {
    ArrayList<Team> teams;

    //EFFECTS: Constructs a League with a list of Team
    public League() {
        teams = new ArrayList<>();
    }

    //EFFECTS: Returns the number of teams in the League
    public int size() {
        return teams.size();
    }

    //EFFECTS: Returns the team at the specified index
    public Team get(int index) {
        EventLog.getInstance().logEvent(new Event("Viewing " + teams.get(index).getTeamName()));
        return teams.get(index);
    }

    //EFFECTS: Returns a list of all teams in the league;
    public ArrayList<Team> getteams() {
        return teams;
    }

    //MODIFIES: this
    //EFFECTS: Adds a Team to the league
    public void add(Team t) {
        teams.add(t);
        EventLog.getInstance().logEvent(new Event("Added " + t.getTeamName() + " to the league"));
    }

    //Converts League into a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns teams in this league as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Team t : teams) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
