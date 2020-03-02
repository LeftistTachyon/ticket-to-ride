package com.github.leftisttachyon.ticket2ride.console;

import com.github.leftisttachyon.ticket2ride.game.*;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A class that tests {@link Game}
 *
 * @see Game
 * @since 1.0.0
 * @author Jed Wang
 */
public class GameTest {
    /**
     * Tests {@link Game#claimRailway(Railway, Map)}
     */
    public void claimRailwayTest() {
        Game g = new Game(Board.createUSBoard(), 1);
        Player[] players = g.getPlayers();
        assertEquals(1, players.length);

        System.out.println(Arrays.toString(players));
        Railway r = new Railway(1, Color.NONE, "Seattle", "Vancouver");

        g.pickRandom();
        g.pickRandom();
    }
}
