package com.github.leftisttachyon.ticket2ride;

import com.github.leftisttachyon.ticket2ride.game.Board;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link Board} class.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public class BoardTest {
    /**
     * Tests the US version of the board
     */
    @Test
    public void usCreationTest() {
        Board b = Board.createUSBoard();
        System.out.println(b.getCities());
        assertEquals(Set.of("Vancouver", "Seattle", "Portland", "San Francisco",
                "Los Angeles", "Calgary", "Helena", "Salt Lake City", "Las Vegas",
                "Phoenix", "Winnipeg", "Duluth", "Omaha", "Denver", "Kansas City",
                "Oklahoma City", "Santa Fe", "El Paso", "Dallas", "Houston",
                "Sault St. Marie", "Chicago", "Saint Louis", "Little Rock",
                "New Orleans", "Toronto", "Pittsburgh", "Nashville", "Atlanta",
                "Montreal", "Boston", "New York", "Washington", "Raleigh",
                "Charleston", "Miami"), b.getCities());
    }
}
