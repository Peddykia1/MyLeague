package persistence;

import model.League;
import model.Player;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
// Some code was taken from the JsonSerializationDemo project on Git
public class JsonReaderTest {


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            League l = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        try {
            League l = new League();
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyLeague.json");
            writer.open();
            writer.write(l);
            writer.close();
            JsonReader reader = new JsonReader("./data/testReaderEmptyLeague.json");
            l = reader.read();
            assertEquals(0, l.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    @Test
    void testReaderGeneralLeague() {
         try {
            League l = new League();
            Team t = new Team("Team1");
            for(int i = 1; i <= 10; i++) {
                t.addplayer(new Player("Player " + i));
            }
            l.add(t);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLeague.json");
            writer.open();
            writer.write(l);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralLeague.json");
            l = reader.read();
            assertEquals(1, l.size());
            t = l.get(0);
            assertEquals("Team1", t.getTeamName());
            List<Player> players = t.getTeam();
            assertEquals(10, players.size());
            for(int i = 1; i <= 10; i++) {
            assertEquals("Player "+ i, players.get(i-1).getName());
        }
    } catch (IOException e) {
        fail("Exception should not have been thrown");
    }
}

}
