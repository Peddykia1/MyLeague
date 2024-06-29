package persistence;

import model.League;
import model.Player;
import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads League from JSON data stored in file
// Some code was taken from the JsonSerializationDemo project on Git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads League from file and returns it;
    // throws IOException if an error occurs reading data from file
    public League read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeague(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses League from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        League l = new League();
        addTeams(l, jsonObject);
        return l;
    }

    // EFFECTS: parses teams from JSON object and adds them to League
    private void addTeams(League l, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(l, nextTeam);
        }
    }

    // MODIFIES: l
    // EFFECTS: parses Team from JSON object and adds it to League
    private void addTeam(League l, JSONObject jsonObject) {
        String teamname = jsonObject.getString("Team name");
        Team t = new Team(teamname);
        JSONArray jsonArray = jsonObject.getJSONArray("Players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(t, nextPlayer);
        }
        l.add(t);
    }

    // MODIFIES: t
    // EFFECTS: parses Player from JSON object and adds it to the Team
    private void addPlayer(Team t, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Player p = new Player(name);
        t.addplayer(p);
    }


}
