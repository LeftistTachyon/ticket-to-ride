package com.github.leftisttachyon.ticket2ride.game;

/**
 * This class represents the various colors of edges and cards.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public enum Color {
    /**
     * An object that represents grey for rails. There is no card analogue of this color.
     */
    NONE(ConsoleColors.BLACK_BRIGHT),
    /**
     * An object that represents white for both rails and cards.
     */
    WHITE(ConsoleColors.WHITE),
    /**
     * An object that represents blue for both rails and cards.
     */
    BLUE(ConsoleColors.BLUE),
    /**
     * An object that represents green for both rails and cards.
     */
    GREEN(ConsoleColors.GREEN_BRIGHT),
    /**
     * An object that represents yellow for both rails and cards.
     */
    YELLOW(ConsoleColors.YELLOW),
    /**
     * An object that represents orange for both rails and cards.
     */
    ORANGE(ConsoleColors.RED),
    /**
     * An object that represents red for both rails and cards.
     */
    RED(ConsoleColors.RED),
    /**
     * An object that represents pink for both rails and cards.
     */
    PINK(ConsoleColors.RED_BRIGHT),
    /**
     * An object that represents black for both rails and cards.
     */
    BLACK(ConsoleColors.WHITE),
    /**
     * An object that represents grey for cards. There is no rail analogue of this color.
     */
    RAINBOW(ConsoleColors.PURPLE_BRIGHT);

    /**
     * The ANSI color to associate with this color.
     */
    private final String ansiColor;

    /**
     * Instantiates a {@link Color} object.
     *
     * @param ansiColor the ANSI color to associate with this {@link Color} object
     */
    Color(String ansiColor) {
        this.ansiColor = ansiColor;
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

    /**
     * Returns the ANSI color associated with this {@link Color} object.
     *
     * @return the ANSI color associated with this {@link Color} object
     */
    public String getANSIColor() {
        return ansiColor;
    }
}
