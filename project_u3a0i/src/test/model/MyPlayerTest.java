package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyPlayerTest {
    Player testplayer;

    @Test
    void testconstructor(){
        testplayer  = new Player("Player 1");
        assertEquals("Player 1", testplayer.getName());
    }
}
