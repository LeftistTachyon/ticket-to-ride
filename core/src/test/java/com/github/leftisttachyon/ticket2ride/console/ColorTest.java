package com.github.leftisttachyon.ticket2ride.console;

import com.github.leftisttachyon.ticket2ride.game.Color;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link Color} class.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public class ColorTest {
    /**
     * Tests {@link Color#toString()}
     */
    @Test
    public void toStringTest() {
        Color c = Color.BLACK;
        assertEquals("BLACK", c.toString());
    }
}
