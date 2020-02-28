package com.github.leftisttachyon.ticket2ride.console;

/**
 * The main class for this application.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public class Main {
    /**
     * The main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(ConsoleColors.CYAN + "Egg?" +
                ConsoleColors.RED_BACKGROUND + "bleed" +
                ConsoleColors.RESET + "reset");

        Thread.sleep(1_000);
        ConsoleColors.clearScreen();

        System.out.println(ConsoleColors.BLUE_UNDERLINED + "Be calm." + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BRIGHT + "Be bold.");

        Thread.sleep(10_000);
        ConsoleColors.clearScreen();
    }

}
