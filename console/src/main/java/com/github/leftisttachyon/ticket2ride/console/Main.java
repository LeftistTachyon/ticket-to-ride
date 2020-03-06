package com.github.leftisttachyon.ticket2ride.console;

import com.github.leftisttachyon.ticket2ride.game.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import static com.github.leftisttachyon.ticket2ride.console.ConsoleColors.*;

/**
 * The main class for this application.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
@Slf4j
public class Main {
    /**
     * The main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        log.trace("One");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            log.trace("Two");
            Board b = Board.createUSBoard();
            log.trace("Three");

            ConsoleColors.clearScreen();
            System.out.print("Enter the number of players: ");
            int players = Integer.parseInt(in.readLine());
            Game g = new Game(b, players);
            g.addActionListener(evt -> System.out.println(GREEN_BRIGHT + evt.getActionCommand() + RESET));
            g.addTurnChangeListener(evt -> System.out.println(BLUE_BRIGHT + "Changed turn to " +
                    evt.getNewTurn() + "." + RESET));

            g.startGame();

            System.out.println(GREEN + "For a listing of all commands, type " + GREEN_BRIGHT + "COMMANDS" + GREEN +
                    "." + RESET);

            String line;
            outer:
            while (true) {
                System.out.print(">> ");

                line = in.readLine();
                if (line == null) break;
                switch (line.toUpperCase()) {
                    case "EXIT":
                        break outer;
                    case "HELP":
                    case "COMMANDS":
                        printCommands();
                        continue outer;
                    case "CLS":
                        ConsoleColors.clearScreen();
                        continue outer;
                    case "RAND":
                        g.pickRandom();
                        continue outer;
                    case "PICK":
                        while (true) {
                            System.out.println("Enter the index of the card you want to pick.");
                            Color[] sideCards = g.getBoard().getSideCards();
                            for (int i = 0; i < sideCards.length; i++) {
                                System.out.println(PURPLE + "#" + (i + 1) + ": " + RESET + sideCards[i]);
                            }
                            System.out.println("To exit picking a card, type " + PURPLE + "0" + RESET);
                            System.out.print(">  ");

                            if ((line = in.readLine()) == null)
                                break outer;

                            if ("0".equals(line)) {
                                continue outer;
                            } else if (line.matches("\\d+")) {
                                int i = Integer.parseInt(line) - 1;
                                if (i < 0 || i >= sideCards.length) {
                                    System.out.println(RED + "That's not a valid index." + RESET);
                                    continue;
                                }

                                if (g.pickCard(i)) {
                                    break;
                                } else {
                                    System.out.println(RED + "You can't pick up that card." + RESET);
                                }
                            } else {
                                System.out.println(RED + "That's not a valid number." + RESET);
                            }
                        }
                        continue outer;
                    case "CLAIM":
                        Player p = g.getCurrentPlayer();
                        int j = 0;

                        System.out.println(BLUE + "List of cities:" + RESET);
                        Set<String> cities = g.getBoard().getCities();
                        for (String s : cities) {
                            System.out.printf("%18s", s);
                            if (++j == 3) {
                                j = 0;
                                System.out.println();
                            }
                        }

                        inner:
                        while (true) {
                            System.out.println("Type the name of the city which the railway starts on.");
                            System.out.println("To exit picking a card, type " + PURPLE + "0" + RESET);
                            System.out.print(">  ");

                            if ((line = in.readLine()) == null)
                                break outer;

                            if ("0".equals(line)) {
                                break;
                            } else if (cities.contains(line)) {
                                while (true) {
                                    List<Railway> unclaimedRailways = g.getBoard().getUnclaimedRailways(line);
                                    int uLen = unclaimedRailways.size();
                                    // TODO: print out avaliable railways
                                    // TODO: prompt the user to pick one
                                    if ((line = in.readLine()) == null)
                                        break outer;

                                    if ("0".equals(line)) {
                                        continue inner;
                                    } else if (line.matches("\\d+")) {
                                        int i = Integer.parseInt(line) - 1;
                                        if (i < 0 || i >= uLen) {
                                            System.out.println(RED + "That's not a valid index." + RESET);
                                            continue;
                                        } /*else if(g.claimRailway(unclaimedRailways.get(i))) {

                                        }*/
                                        // TODO: ask the user for what kinds of cards they're going to use
                                        break;
                                    } else {
                                        System.out.println(RED + "That's not a valid number." + RESET);
                                    }
                                }
                                break;
                            } else {
                                System.out.println(RED + "That is not a valid city." + RESET);
                            }
                        }

                        continue outer;
                    case "RETURN":
                        p = g.getCurrentPlayer();
                        List<Route> routes = p.getRoutes();
                        while (true) {
                            System.out.println("Enter the index of the route card that you would like to return.");
                            int len = routes.size();
                            for (int k = 0; k < len; k++) {
                                Route r = routes.get(k);
                                System.out.println(PURPLE + "#" + (k + 1) + ": " + RESET + r.getDest1() + " to " +
                                        r.getDest2());
                            }
                            System.out.println("To exit returning a card, type " + PURPLE + "0" + RESET);
                            System.out.print(">  ");

                            if ((line = in.readLine()) == null)
                                break outer;

                            if ("0".equals(line)) {
                                continue outer;
                            } else if (line.matches("\\d+")) {
                                int i = Integer.parseInt(line) - 1;
                                if (i < 0 || i >= len) {
                                    System.out.println(RED + "That's not a valid index." + RESET);
                                }

                                g.returnRoute(p, routes.get(i));
                                break;
                            } else {
                                System.out.println(RED + "That's not a valid number." + RESET);
                            }
                        }
                        continue outer;
                    case "DRAW":
                        g.drawRoutes();
                        continue outer;
                }

                System.out.println(ConsoleColors.RED + "Unknown command." + RESET);
            }
        } catch (IOException e) {
            log.warn("An IOException was thrown", e);
        }
    }

    /**
     * Prints all statements that can be executed.
     */
    private static void printCommands() {
        System.out.println(BLUE + "Commands:\n========");
        System.out.println(PURPLE + "EXIT" + RESET);
        System.out.println("Exits this game.");
        System.out.println(PURPLE + "HELP or COMMANDS" + RESET);
        System.out.println("Prints out a listing of all commands that can be executed. It's what you're looking at right now.");
        System.out.println(PURPLE + "CLS" + RESET);
        System.out.println("Clears the screen.");
        System.out.println(PURPLE + "RAND" + RESET);
        System.out.println("Picks a random card from the stack.");
        System.out.println(PURPLE + "PICK" + RESET);
        System.out.println("Picks a selected card from the side pile.");
        System.out.println(PURPLE + "CLAIM" + RESET);
        System.out.println("Claims a certain railway.");
        System.out.println(PURPLE + "RETURN" + RESET);
        System.out.println("Returns a route card back, if possible.");
        System.out.println(PURPLE + "DRAW" + RESET);
        System.out.println("Draws three new route cards.");
    }
}
