package com.github.leftisttachyon.ticket2ride.game;

/**
 * This class represents the various colors of edges and cards.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public enum Color {
    NONE, WHITE, BLUE, GREEN, YELLOW, ORANGE, RED, PINK, BLACK, RAINBOW;

    /**
     * Instantiates a {@link Color} object.
     */
    Color() {
    }

    /**
     * Determines whether this {@link Color} object would match with the given {@link Color} object.
     *
     * @param c the {@link Color} to check with
     * @return whether this {@link Color} matches with the given one
     */
    public boolean accepts(Color c) {
        if (c == NONE) {
            throw new IllegalArgumentException("Trying to match with NONE color");
        } else return this == NONE || this == c || c == RAINBOW;
    }
}
