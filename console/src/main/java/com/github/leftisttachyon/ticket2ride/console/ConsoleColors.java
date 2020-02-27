package com.github.leftisttachyon.ticket2ride.console;

/**
 * A class that stores ANSI color codes.
 *
 * @author shakram02
 * @since 1.0.0
 */
public final class ConsoleColors {
    // Reset
    /**
     * The color reset code.
     */
    public static final String RESET = "\033[0m";  // Text Reset
    // Regular Colors
    /**
     * The code for setting the font color to black.
     */
    public static final String BLACK = "\033[0;30m";   // BLACK
    /**
     * The code for setting the font color to red.
     */
    public static final String RED = "\033[0;31m";     // RED
    /**
     * The code for setting the font color to green.
     */
    public static final String GREEN = "\033[0;32m";   // GREEN
    /**
     * The code for setting the font color to yellow.
     */
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    /**
     * The code for setting the font color to blue.
     */
    public static final String BLUE = "\033[0;34m";    // BLUE
    /**
     * The code for setting the font color to purple.
     */
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    /**
     * The code for setting the font color to cyan.
     */
    public static final String CYAN = "\033[0;36m";    // CYAN
    /**
     * The code for setting the font color to white.
     */
    public static final String WHITE = "\033[0;37m";   // WHITE
    // Bold
    /**
     * The code for setting the font color to black and makes the font bold.
     */
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    /**
     * The code for setting the font color to red and makes the font bold.
     */
    public static final String RED_BOLD = "\033[1;31m";    // RED
    /**
     * The code for setting the font color to green and makes the font bold.
     */
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    /**
     * The code for setting the font color to yellow and makes the font bold.
     */
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    /**
     * The code for setting the font color to blue and makes the font bold.
     */
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    /**
     * The code for setting the font color to purple and makes the font bold.
     */
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    /**
     * The code for setting the font color to cyan and makes the font bold.
     */
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    /**
     * The code for setting the font color to white and makes the font bold.
     */
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
    // Underline
    /**
     * The code for setting the font color to black and makes the font underlined.
     */
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    /**
     * The code for setting the font color to red and makes the font underlined.
     */
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    /**
     * The code for setting the font color to green and makes the font underlined.
     */
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    /**
     * The code for setting the font color to yellow and makes the font underlined.
     */
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    /**
     * The code for setting the font color to blue and makes the font underlined.
     */
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    /**
     * The code for setting the font color to purple and makes the font underlined.
     */
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    /**
     * The code for setting the font color to cyan and makes the font underlined.
     */
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    /**
     * The code for setting the font color to white and makes the font underlined.
     */
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE
    // Background
    /**
     * The code for setting the background color to black.
     */
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    /**
     * The code for setting the background color to red.
     */
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    /**
     * The code for setting the background color to green.
     */
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    /**
     * The code for setting the background color to yellow.
     */
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    /**
     * The code for setting the background color to blue.
     */
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    /**
     * The code for setting the background color to purple.
     */
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    /**
     * The code for setting the background color to cyan.
     */
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    /**
     * The code for setting the background color to white.
     */
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE
    // High Intensity
    /**
     * The code for setting the font color to a bright black.
     */
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    /**
     * The code for setting the font color to a bright red.
     */
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    /**
     * The code for setting the font color to a bright green.
     */
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    /**
     * The code for setting the font color to a bright yellow.
     */
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    /**
     * The code for setting the font color to a bright blue.
     */
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    /**
     * The code for setting the font color to a bright purple.
     */
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    /**
     * The code for setting the font color to a bright cyan.
     */
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    /**
     * The code for setting the font color to a bright white.
     */
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE
    // Bold High Intensity
    /**
     * The code for setting the font color to a bright black and makes the font bold.
     */
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    /**
     * The code for setting the font color to a bright red and makes the font bold.
     */
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    /**
     * The code for setting the font color to a bright green and makes the font bold.
     */
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    /**
     * The code for setting the font color to a bright yellow and makes the font bold.
     */
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    /**
     * The code for setting the font color to a bright blue and makes the font bold.
     */
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    /**
     * The code for setting the font color to a bright purple and makes the font bold.
     */
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    /**
     * The code for setting the font color to a bright cyan and makes the font bold.
     */
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    /**
     * The code for setting the font color to a bright white and makes the font bold.
     */
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    // High Intensity backgrounds
    /**
     * The code for setting the background color to a bright black.
     */
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    /**
     * The code for setting the background color to a bright red.
     */
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    /**
     * The code for setting the background color to a bright green.
     */
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    /**
     * The code for setting the background color to a bright yellow.
     */
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    /**
     * The code for setting the background color to a bright blue.
     */
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    /**
     * The code for setting the background color to a bright purple.
     */
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    /**
     * The code for setting the background color to a bright cyan.
     */
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    /**
     * The code for setting the background color to a bright white.
     */
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    /**
     * No instantiation for you!
     */
    private ConsoleColors() {
    }
}
