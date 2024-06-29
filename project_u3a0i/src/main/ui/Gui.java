package ui;

import model.Event;
import model.EventLog;
import model.League;
import model.Player;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Gui {
    private static final String JSON_STORE = "./data/league.json";
    JButton button1 = new JButton("Add Team");
    JButton button2 = new JButton("View Starters");
    JButton button4 = new JButton("Save");
    JButton button5 = new JButton("Load");
    JButton button3 = new JButton("View Substitutes");
    DefaultListModel listplayer = new DefaultListModel();
    DefaultListModel listteam = new DefaultListModel();
    JList<String> jlistplayer;
    JList<String> jlistteam;
    int selectteamindex;
    String tname;
    String pname;
    Player player;
    League league = new League();
    Team team;
    Team selectteam;
    List<Player> starters;
    List<Player> substitutes;
    static ImageIcon image = new ImageIcon(Gui.class.getResource("leaguepic.jpeg"));
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public Gui() {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
        frame.setSize(500, 500);
        button1.setSize(100,50);
        button2.setSize(100,50);
        button3.setSize(100,50);
        button4.setSize(100,50);
        button5.setSize(100,50);
        button1.setLocation(375, 122);
        button2.setLocation(375, 167);
        button3.setLocation(375, 213);
        button4.setLocation(375, 258);
        button5.setLocation(375, 304);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button1) {
                    tname = JOptionPane.showInputDialog("Enter name of Team: ");
                    team = new Team(tname);
                    for (int i = 0; i < 15; i++) {
                        pname = JOptionPane.showInputDialog("Enter name of Player: " + (i + 1));
                        player = new Player(pname);
                        team.addplayer(player);
                        listplayer.addElement(player.getName());
                    }
                    league.add(team);
                    listteam.addElement(team.getTeamName());
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button2) {
                    selectteamindex = Integer.parseInt(JOptionPane.showInputDialog("Which team's starting players "
                            + "would " + "you like to view" + "(1=first team , 2=second team ,..."));
                    if (selectteamindex > league.size()) {
                        JOptionPane.showMessageDialog(null, "Invalid input");
                    }
                    selectteam = league.get(selectteamindex - 1);
                    starters = selectteam.getstarters();
                    for (int i = 0; i < starters.size(); i++) {
                        JOptionPane.showMessageDialog(null, starters.get(i).getName());
                    }
                    }
                }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button3) {
                    selectteamindex = Integer.parseInt(JOptionPane.showInputDialog("Which team's substitute players "
                            + "would " + "you like to view" + "(1=first team , 2=second team ,..."));
                    if (selectteamindex > league.size()) {
                        JOptionPane.showMessageDialog(null, "Invalid input");
                    }
                    selectteam = league.get(selectteamindex - 1);
                    substitutes = selectteam.getsubs();
                    for (int i = 0; i < substitutes.size(); i++) {
                        JOptionPane.showMessageDialog(null, substitutes.get(i).getName());
                    }
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button4) {
                        saveLeague();
                }
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button5) {
                    try {
                        loadLeague();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Cannot load league from "
                                + JSON_STORE);
                    }

                }
            }
        });
        jlistteam = new JList<>(listteam);
        jlistplayer = new JList<>(listplayer);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        JPanel panel1 = new JPanel();
        JScrollPane scrollPane1 = new JScrollPane();
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane1.setViewportView(jlistplayer);
        scrollPane2.setViewportView(jlistteam);
        scrollPane1.setPreferredSize(new Dimension(250, 80));
        scrollPane1.setLocation(500, 37);
        scrollPane2.setPreferredSize(new Dimension(250, 80));
        jlistplayer.setLayoutOrientation(JList.VERTICAL_WRAP);
        jlistteam.setLayoutOrientation(JList.VERTICAL_WRAP);
        panel1.add(scrollPane2);
        panel1.add(scrollPane1);
        frame.add(panel1);
    }

    public void saveLeague() {
        try {
            jsonWriter.open();
            jsonWriter.write(league);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved league to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }

    public void loadLeague() throws IOException {
        league = jsonReader.read();
        for (Team team: league.getteams()) {
            listteam.addElement(team.getTeamName());
            for (Player player: team.getTeam()) {
                listplayer.addElement(player.getName());
            }
        }
        JOptionPane.showMessageDialog(null, "league loaded from " + JSON_STORE);
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, null, "league", JOptionPane.INFORMATION_MESSAGE, image);
        //Source of image: https://www.dreamstime.com/stock-illustration-basketball-team-player-dunking-dripping-ball-action-illustration-graphic-vector-image98509389
        Gui g = new Gui();
    }
}