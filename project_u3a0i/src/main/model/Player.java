package model;

import org.json.JSONObject;

// Represents each individual player in the team
public class Player {
    String name; // Name of player

    //EFFECTS: Constructs a new player where the user inputs the name of the player
    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //EFFECTS: Converts Player into a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }
}
