package com.github.leftisttachyon.ticket2ride.console;

import com.github.leftisttachyon.ticket2ride.game.Board;
import com.github.leftisttachyon.ticket2ride.game.Color;
import com.github.leftisttachyon.ticket2ride.game.Railway;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Tests the {@link Board} class.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
@Slf4j
public class BoardTest {
    /**
     * Tests the US version of the board
     *
     * @see Board#getCities()
     */
    @Test
    public void usCreationTest() {
        Board b = Board.createUSBoard();
        log.info("{}", b.getCities());
        assertEquals(Set.of("Vancouver", "Seattle", "Portland", "San Francisco",
                "Los Angeles", "Calgary", "Helena", "Salt Lake City", "Las Vegas",
                "Phoenix", "Winnipeg", "Duluth", "Omaha", "Denver", "Kansas City",
                "Oklahoma City", "Santa Fe", "El Paso", "Dallas", "Houston",
                "Sault St. Marie", "Chicago", "Saint Louis", "Little Rock",
                "New Orleans", "Toronto", "Pittsburgh", "Nashville", "Atlanta",
                "Montreal", "Boston", "New York", "Washington", "Raleigh",
                "Charleston", "Miami"), b.getCities());
    }

    /**
     * Tests duplicated {@link Railway}s in {@link Board}s
     *
     * @see Board#getRailways(String)
     */
    @Test
    public void routeDupeTest() {
        List<Railway> l = new LinkedList<>();
        l.add(new Railway(1, Color.WHITE, "a", "b"));
        l.add(new Railway(1, Color.WHITE, "a", "b"));
        Board b = new Board(l, Collections.emptyList());

        log.info("{}", b.getCities());
        assertEquals(Set.of("a", "b"), b.getCities());

        assertEquals(b.getRailways("a"), l);
        assertEquals(b.getRailways("b"), l);
    }

    /**
     * Tests {@link Board#getAdjacents(String)}
     */
    @Test
    public void adjacentTest() {
        List<Railway> l = new LinkedList<>();
        l.add(new Railway(1, Color.WHITE, "a", "b"));
        l.add(new Railway(1, Color.WHITE, "a", "c"));
        l.add(new Railway(1, Color.WHITE, "a", "d"));
        l.add(new Railway(1, Color.WHITE, "b", "c"));
        Board b = new Board(l, Collections.emptyList());

        assertEquals(Set.of("a", "b", "c", "d"), b.getCities());

        assertEquals(List.of("b", "c", "d"), b.getAdjacents("a"));
    }

    /**
     * Tests {@link Board#getRailway(Railway)}
     */
    @Test
    public void getAdjacentTest() {
        List<Railway> l = new LinkedList<>();
        Railway e = new Railway(1, Color.WHITE, "a", "b");
        l.add(e);
        l.add(new Railway(1, Color.WHITE, "a", "b"));
        Board b = new Board(l, Collections.emptyList());

        Railway e1 = new Railway(1, Color.WHITE, "b", "a");
        log.info("{} vs. {}", e.hashCode(), e1.hashCode());

        assertEquals(e.hashCode(), b.getRailway(e1).hashCode());
    }

    /**
     * Tests {@link Board#getRailways(String)}
     */
    @Test
    public void getRailwaysSpeedtest() {
        Board b = Board.createUSBoard();
        Set<String> cities = b.getCities();
        double start, total;

        start = System.nanoTime();
        for (String s : cities) {
            b.getRailways(s);
        }
        total = System.nanoTime() - start;
        total /= 1_000_000;
        System.out.printf("Original: %.3f ms%n", total);

        start = System.nanoTime();
        for (String s : cities) {
            b.altGetRailways(s);
        }
        total = System.nanoTime() - start;
        total /= 1_000_000;
        System.out.printf("Alt:      %.3f ms%n", total);
    }

    /**
     * Tests {@link Board#getUnclaimedRailways(String)}
     */
    @Test
    public void getUnclaimedRailwaysSpeedtest() {
        /*Board b = Board.createUSBoard();
        Set<String> cities = b.getCities();
        double start, total;

        start = System.nanoTime();
        for (String s : cities) {
            b.getUnclaimedRailways(s);
        }
        total = System.nanoTime() - start;
        total /= 1_000_000;
        System.out.printf("Original: %.3f ms%n", total);

        start = System.nanoTime();
        for (String s : cities) {
            b.altGetUnclaimedRailways(s);
        }
        total = System.nanoTime() - start;
        total /= 1_000_000;
        System.out.printf("Alt:      %.3f ms%n", total);*/
    }
}
