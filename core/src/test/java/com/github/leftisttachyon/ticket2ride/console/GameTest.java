package com.github.leftisttachyon.ticket2ride.console;

import com.github.leftisttachyon.ticket2ride.game.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static com.github.leftisttachyon.ticket2ride.game.Color.NONE;
import static org.junit.Assert.*;

/**
 * A class that tests {@link Game}
 *
 * @see Game
 * @since 1.0.0
 * @author Jed Wang
 */
@Slf4j
public class GameTest {
    /**
     * Tests {@link Game#claimRailway(Railway, Map)}
     */
    @Test
    public void claimRailwayTest() {
        Game g = new Game(Board.createUSBoard(), 1);
        g.addTurnChangeListener(evt -> log.info("NEW TURN: {}", evt.getNewTurn()));
        g.addActionListener(evt -> log.info("ACTION: {}", evt.getActionCommand()));

        Player[] players = g.getPlayers();
        assertEquals(1, players.length);

        log.info("{}", Arrays.toString(players));

        Player p = g.getPlayer(0);
        assertEquals(0, p.getNumCards());
        g.pickRandom();
        g.pickRandom();
        assertEquals(2, p.getNumCards());

        assertTrue(p.getOwnedRailways().isEmpty());
        log.info("{}", p.getCards());
        Railway railway = new Railway(2, NONE, "Nashville", "Saint Louis");
        assertTrue(g.claimRailway(railway, p.getCards()));
        assertEquals(0, p.getNumCards());
        assertFalse(p.getOwnedRailways().isEmpty());
        assertEquals(2, p.getPoints());
    }

    /**
     * Tests {@link Game#pickCard(int)}
     */
    @Test
    public void pickSideTest() {
        Game g = new Game(Board.createUSBoard(), 1);
        g.addTurnChangeListener(evt -> log.info("NEW TURN: {}", evt.getNewTurn()));
        g.addActionListener(evt -> log.info("ACTION: {}", evt.getActionCommand()));

        Player p = g.getPlayer(0);
        assertEquals(0, p.getNumCards());

        Color[] sideCards = g.getBoard().getSideCards();
        log.info("{}", Arrays.toString(sideCards));
        Color temp = sideCards[0];
        assertTrue(g.pickCard(0));
        log.info("{}", Arrays.toString(g.getBoard().getSideCards()));

        assertEquals(1, p.getNumCards());
        assertEquals(1, (int) p.getCards().get(temp));
    }
}
