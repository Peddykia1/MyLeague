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
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            League l = new League();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // passIOException
        }
    }

    @Test
    void testWriterEmptyLeague() {
        try {
            League l = new League();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLeague.json");
            writer.open();
            writer.write(l);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyLeague.json");
            l = reader.read();
            assertEquals(0, l.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    @Test
    void testWriterGeneralLeague() {
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
