package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTeamTest {
    public Team testTeam;

    @BeforeEach
    void runBefore(){
        testTeam = new Team("Team 1");
    }

    @Test
    void Testconstructor(){
        assertEquals("Team 1", testTeam.getTeamName());
    }

    @Test
    void Testaddplayer1(){
        testTeam.addplayer(new Player("Player 1"));
        assertEquals(1,testTeam.getTeam().size());
    }

    @Test
    void Testaddplayer2(){
        for (int i = 1; i <= 20; i++) {
            testTeam.addplayer(new Player("player " + i));
        }
        assertEquals(15,testTeam.getTeam().size());
    }

    @Test
    void Testgetstarters1() {
        testTeam.addplayer(new Player("Player 1"));
        List<Player> x = testTeam.getstarters();
        assertEquals(0, x.size());
    }

    @Test
    void Testgetstarters2() {
        for(int i = 1; i <= 8; i++) {
            testTeam.addplayer(new Player("Player "+i));
        }
        List<Player> x = testTeam.getstarters();
        assertEquals(5, x.size());
    }
    @Test
    void Testgetsubs1() {
        for(int i = 1; i <= 10; i++) {
            testTeam.addplayer(new Player("Player "+i));
        }
        List<Player> x = testTeam.getsubs();
        assertEquals(0, x.size());
    }

    @Test
    void Testgetsubs2() {
        for(int i = 1; i <= 15; i++) {
            testTeam.addplayer(new Player("Player "+i));
        }
        List<Player> x = testTeam.getsubs();
        assertEquals(10, x.size());
    }

    @Test
    void TestgetTeamCaptain(){
        Player p1 = new Player("player 1");
        testTeam.addplayer(p1);
        assertEquals(p1, testTeam.getTeamCaptain());
    }

    @Test
    void TestsetTeamName(){
       testTeam.setTeamName("Actual team1");
       assertEquals("Actual team1", testTeam.getTeamName());
    }

}

