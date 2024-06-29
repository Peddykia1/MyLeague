package ui;

import model.League;
import model.Player;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//Represents the League application
public class LeagueApp {
    private static final String JSON_STORE = "./data/league.json";
    Scanner input = new Scanner(System.in);
    League teams;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: constructs League and runs application
    public LeagueApp() {
        teams = new League();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLeague();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runLeague() {
        boolean keepgoing = true;
        String command = null;

        while (keepgoing) {
            displayMenu();
            command = input.next();
            if (command.equals("G")) {
                keepgoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("A")) {
            createteam();
        } else if (command.equals("B") && teams.size() > 0) {
            getteamcaptain();
        } else if (command.equals("C") && teams.size() > 0) {
            displaystartingplayers();
        } else if (command.equals("D") && teams.size() > 0) {
            displaysubstituteplayers();
        } else if (command.equals("E")) {
            saveLeague();
        } else if (command.equals("F")) {
            loadLeague();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves the League to file
    public void saveLeague() {
        try {
            jsonWriter.open();
            jsonWriter.write(teams);
            jsonWriter.close();
            System.out.println("Saved this league to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadLeague() {
        try {
            teams = jsonReader.read();
            System.out.println("Loaded league from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompt user for name of team and adds to League
    public void createteam() {
        System.out.println("Enter name of team.");
        String teamname = input.next();
        Team team = new Team(teamname);
        System.out.println("Enter name of 5 starting players followed by 10 substitute players.");
        for (int i = 0; i < 15; i++) {
            String playername = input.next();
            Player p = new Player(playername);
            team.addplayer(p);
        }
        teams.add(team);
    }

    // EFFECTS: prompt user for selection of a team and displays the corresponding team captain
    public void getteamcaptain() {
        System.out.println("Which team captain would you like to view"
                + "(1=first team captain, 2=second team captain,...");
        int index = input.nextInt();
        if (index > teams.size()) {
            System.out.println("Invalid input");
        } else {
            Team thisteam = teams.get(index - 1);
            System.out.println(thisteam.getTeamCaptain().getName());
        }
    }

    // EFFECTS: prompt user for selection of a team and displays the corresponding team starting players
    public void displaystartingplayers() {
        System.out.println("Which team's starting players would you like to view"
                + "(1=first team , 2=second team ,...");
        int index = input.nextInt();
        if (index > teams.size()) {
            System.out.println("Invalid input");
        } else {
            Team thisteam = teams.get(index - 1);
            List<Player> startingplayers = thisteam.getstarters();
            for (Player p: startingplayers) {
                System.out.println(p.getName());
            }
        }
    }

    // EFFECTS: prompt user for selection of a team and displays the corresponding team substitute players
    public void displaysubstituteplayers() {
        System.out.println("Which team's substitue players would you like to view"
                + "(1=first team , 2=second team ,...");
        int index = input.nextInt();
        if (index > teams.size()) {
            System.out.println("Invalid input");
        } else {
            Team thisteam = teams.get(index - 1);
            List<Player> substituteplayers = thisteam.getsubs();
            for (Player p: substituteplayers) {
                System.out.println(p.getName());
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tA -> Create new Team");
        System.out.println("\tB -> View a team's captain");
        System.out.println("\tC -> View a team's starting players");
        System.out.println("\tD -> View a team's substitute players");
        System.out.println("\tE -> save league to file");
        System.out.println("\tF -> load league from file");
        System.out.println("\tG -> quit");
    }
}
