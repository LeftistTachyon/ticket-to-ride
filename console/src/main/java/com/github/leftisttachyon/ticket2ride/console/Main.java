package com.github.leftisttachyon.ticket2ride.console;

import com.github.leftisttachyon.ticket2ride.game.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
            List<String> eventLog = new LinkedList<>();

            log.trace("Two");
            Board b = Board.createUSBoard();
            log.trace("Three");

            ConsoleColors.clearScreen();
            System.out.print("Enter the number of players: ");
            int players = Integer.parseInt(in.readLine());
            Game g = new Game(b, players);
            g.addActionListener(evt -> {
                String x = GREEN_BRIGHT + evt.getActionCommand() + RESET;
                System.out.println(x);
                eventLog.add(0, x);
            });
            g.addTurnChangeListener(evt -> {
                String x = BLUE_BRIGHT + "Changed turn to " + evt.getNewTurn() + "." + RESET;
                System.out.println(x);
                eventLog.add(0, x);
            });

            for (int i = 0; i < players; i++) {
                System.out.println(CYAN + "Enter the name of person #" + (i + 1) + ":" + RESET);
                System.out.print(">  ");

                g.getPlayer(i).setName(in.readLine());
            }

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
                    case "CLEAR":
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
                        inner:
                        while (true) {
                            System.out.println("Type the name of the city which the railway starts on.");
                            System.out.println(BLUE + "List of cities:" + RESET);
                            Set<String> cities = g.getBoard().getCities();
                            int j = 0;

                            for (String s : cities) {
                                System.out.printf("%-18s", s);
                                if (++j == 3) {
                                    j = 0;
                                    System.out.println();
                                }
                            }
                            System.out.println("To exit picking a city, type " + PURPLE + "0" + RESET);
                            System.out.print(">  ");

                            if ((line = in.readLine()) == null)
                                break outer;

                            if ("0".equals(line)) {
                                break;
                            } else if (cities.contains(line)) {
                                List<Railway> unclaimedRailways = g.getBoard().getUnclaimedRailways(line);
                                int uLen = unclaimedRailways.size();

                                while (true) {
                                    System.out.println("Type the index of the railway that you want to claim.");
                                    for (int k = 0; k < uLen; k++) {
                                        Railway r = unclaimedRailways.get(k);
                                        System.out.printf("%s#%d:%s %d units; %-6s; %s to %-18s%n", PURPLE, k + 1, RESET,
                                                r.getLength(), r.getColor().toString(), line, r.getOtherDestination(line));
                                    }
                                    System.out.println("To exit picking a railway, type " + PURPLE + "0" + RESET);
                                    System.out.print(">  ");

                                    if ((line = in.readLine()) == null)
                                        break outer;

                                    if ("0".equals(line)) {
                                        continue inner;
                                    } else if (line.matches("\\d+")) {
                                        int i = Integer.parseInt(line) - 1;
                                        if (i < 0 || i >= uLen) {
                                            System.out.println(RED + "That's not a valid index." + RESET);
                                            continue;
                                        } else {
                                            HashMap<Color, Integer> cards = g.getCurrentPlayer().getCards(),
                                                    toUse = new HashMap<>();

                                            for (Map.Entry<Color, Integer> entry : cards.entrySet()) {
                                                if (entry.getValue() == 0) {
                                                    continue;
                                                }

                                                while (true) {
                                                    System.out.println("How many " + CYAN + entry.getKey() + RESET +
                                                            " cards do you want to use? You have " + PURPLE +
                                                            entry.getValue() + RESET + ".");
                                                    System.out.print(">  ");

                                                    if ((line = in.readLine()) == null)
                                                        break outer;

                                                    if (line.matches("\\d+")) {
                                                        int k = Integer.parseInt(line);
                                                        if (k > entry.getValue()) {
                                                            System.out.println(RED + "You don't have that many of those cards."
                                                                    + RESET);
                                                        } else {
                                                            toUse.put(entry.getKey(), k);
                                                            break;
                                                        }
                                                    } else {
                                                        System.out.println(RED + "That's not a valid number." + RESET);
                                                    }
                                                }
                                            }

                                            if (!g.claimRailway(unclaimedRailways.get(i), toUse)) {
                                                System.out.println(RED + "You can't claim that railway right now." + RESET);
                                            }
                                        }

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
                                System.out.println(CYAN + "#" + (k + 1) + RESET + ": " + PURPLE + r.getDest1() +
                                        RESET + " to " + PURPLE + r.getDest2() + RESET + " : " + r.getValue());
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
                    case "SEEROUTES":
                        System.out.println(BLUE + "Currently owned routes:");
                        for (Route r : g.getCurrentPlayer().getRoutes()) {
                            System.out.println(PURPLE + r.getDest1() + RESET + " to " + PURPLE + r.getDest2() +
                                    RESET + " : " + r.getValue());
                        }
                        continue outer;
                    case "SEECARDS":
                        System.out.println(BLUE + "Currently owned cards:" + PURPLE);
                        for (Map.Entry<Color, Integer> entry : g.getCurrentPlayer().getCards().entrySet()) {
                            System.out.println(entry.getValue() + RESET + " " + entry.getKey() + "s" + PURPLE);
                        }
                        System.out.print(RESET);
                        continue outer;
                    case "VIEWMAP":
                        while (true) {
                            System.out.println("Type the name of the city which you want to take a look at.");
                            System.out.println(BLUE + "List of cities:" + RESET);
                            Set<String> cities = g.getBoard().getCities();
                            int j = 0;

                            for (String s : cities) {
                                System.out.printf("%-18s", s);
                                if (++j == 3) {
                                    j = 0;
                                    System.out.println();
                                }
                            }
                            System.out.println("To exit viewing the map, type " + PURPLE + "0" + RESET);
                            System.out.print(">  ");

                            if ((line = in.readLine()) == null)
                                break outer;

                            if ("0".equals(line)) {
                                break;
                            } else if (cities.contains(line)) {
                                // view the outward railways
                                for (Railway r : g.getBoard().getRailways(line)) {
                                    System.out.printf("%s%s%s to %s%-18s%s; %-6s; %d units%n", PURPLE, line,
                                            RESET, PURPLE, r.getOtherDestination(line), RESET, r.getColor().toString(),
                                            r.getLength());
                                    int claimedBy = r.getClaimedBy();
                                    if (claimedBy == -1) {
                                        System.out.println("\tOwned by " + YELLOW + "nobody" + RESET);
                                    } else if (claimedBy == Integer.MAX_VALUE) {
                                        System.out.println("\tOwned by " + YELLOW + g.getPlayer(claimedBy) + RESET);
                                    } else {
                                        System.out.println("\tOwned by " + YELLOW + "the game" + RESET);
                                    }
                                }
                            } else {
                                System.out.println(RED + "That is not a valid city." + RESET);
                            }
                        }
                        continue outer;
                    case "SEERAILS":
                        for (Railway r : g.getCurrentPlayer().getOwnedRailways()) {
                            System.out.printf("%s%-18s%s to %s%-18s%s; %-6s; %d units%n", PURPLE, r.getDest1(),
                                    RESET, PURPLE, r.getDest2(), RESET, r.getColor().toString(), +r.getLength());
                        }
                        continue outer;
                    case "PRINTLOG":
                        while (true) {
                            System.out.println("Enter how many lines to print out");
                            System.out.print(">  ");

                            if((line = in.readLine()) == null)
                                break outer;

                            if (line.matches("\\d+")) {
                                int i = Math.min(Integer.parseInt(line), eventLog.size());
                                for (int j = 0; j < i; j++) {
                                    System.out.println(eventLog.get(j));
                                }

                                break;
                            } else {
                                System.out.println(RED + "That's an invalid number." + RESET);
                            }
                        }
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
        System.out.println(PURPLE + "CLS or CLEAR" + RESET);
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
        System.out.println(PURPLE + "SEEROUTES" + RESET);
        System.out.println("Prints out all the route cards that the current player has.");
        System.out.println(PURPLE + "SEECARDS" + RESET);
        System.out.println("Prints out all the train cards that the current player has.");
        System.out.println(PURPLE + "SEERAILS" + RESET);
        System.out.println("Prints out all the railways that the current player has.");
        System.out.println(PURPLE + "VIEWMAP" + RESET);
        System.out.println("Views a portion of the map.");
        System.out.println(PURPLE + "PRINTLOG" + RESET);
        System.out.println("Prints out the last number of log entries.");
    }
}
