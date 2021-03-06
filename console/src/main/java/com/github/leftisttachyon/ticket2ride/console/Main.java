package com.github.leftisttachyon.ticket2ride.console;

import com.github.leftisttachyon.ticket2ride.game.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static com.github.leftisttachyon.ticket2ride.game.ConsoleColors.*;

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
            LinkedList<String> eventLog = new LinkedList<>();

            boolean[] loop = {true};

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
                eventLog.add(x);

                if ("ENDGAME".equals(evt.getActionCommand())) {
                    loop[0] = false;
                }
            });
            g.addTurnChangeListener(evt -> {
                String x = BLUE_BRIGHT + "Changed turn to " + evt.getNewTurn() + "." + RESET;
                System.out.println(x);
                eventLog.add(x);
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
            while (loop[0]) {
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
                    case "RANDCARD":
                        g.pickRandom();
                        continue outer;
                    case "PICKCARD":
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
                    case "CLAIMRAIL":
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
                                        System.out.println("#" + (k + 1) + ": " + unclaimedRailways.get(k).toString());
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
                    case "RETURNROUTE":
                        Player p = g.getCurrentPlayer();
                        if (!p.canReturnCard()) {
                            System.out.println(RED + "You can't return any route cards right now." + RESET);
                            continue outer;
                        }
                        List<Route> routes = p.getRoutes();
                        while (true) {
                            System.out.println("Enter the index of the route card that you would like to return.");
                            int len = routes.size();
                            for (int k = 0; k < len; k++) {
                                System.out.println(CYAN + "#" + (k + 1) + RESET + ": " + routes.get(k).toString());
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

                                if (g.returnRoute(p, routes.get(i))) {
                                    break;
                                } else {
                                    System.out.println(RED + "You can't return that route card." + RESET);
                                }
                            } else {
                                System.out.println(RED + "That's not a valid number." + RESET);
                            }
                        }
                        continue outer;
                    case "DRAWROUTES":
                        if (!g.drawRoutes()) {
                            System.out.println(RED + "You can't draw any routes." + RESET);
                        }
                        continue outer;
                    case "SEEROUTES":
                        System.out.println(BLUE + "Currently owned routes:");
                        for (Route r : g.getCurrentPlayer().getRoutes()) {
                            System.out.println(r);
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
                                    System.out.println(r);
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
                            System.out.println(r);
                        }
                        continue outer;
                    case "GETTRAINS":
                        p = g.getCurrentPlayer();
                        System.out.println("You currently have " + PURPLE + p.getTrains() + RESET + " trains");
                        continue outer;
                    case "PRINTLOG":
                        while (true) {
                            System.out.println("Enter how many lines to print out:");
                            System.out.print(">  ");

                            if ((line = in.readLine()) == null)
                                break outer;

                            if (line.matches("\\d+")) {
                                int size = eventLog.size();
                                int i = Math.min(Integer.parseInt(line), size);
                                for (int j = size - i; j < size; j++) {
                                    System.out.println(eventLog.get(j));
                                }

                                break;
                            } else {
                                System.out.println(RED + "That's an invalid number." + RESET);
                            }
                        }
                        continue outer;
                    case "GETPOINTS":
                        for (Player player : g.getPlayers()) {
                            System.out.println(CYAN + player.getName() + RESET + " has " + PURPLE + player.getPoints() +
                                    RESET + " points");
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
        System.out.println(PURPLE + "RANDCARD" + RESET);
        System.out.println("Picks a random card from the stack.");
        System.out.println(PURPLE + "PICKCARD" + RESET);
        System.out.println("Picks a selected card from the side pile.");
        System.out.println(PURPLE + "CLAIMRAIL" + RESET);
        System.out.println("Claims a certain railway.");
        System.out.println(PURPLE + "RETURNROUTE" + RESET);
        System.out.println("Returns a route card back, if possible.");
        System.out.println(PURPLE + "DRAWROUTES" + RESET);
        System.out.println("Draws three new route cards.");
        System.out.println(PURPLE + "SEEROUTES" + RESET);
        System.out.println("Prints out all the route cards that the current player has.");
        System.out.println(PURPLE + "SEECARDS" + RESET);
        System.out.println("Prints out all the train cards that the current player has.");
        System.out.println(PURPLE + "SEERAILS" + RESET);
        System.out.println("Prints out all the railways that the current player has.");
        System.out.println(PURPLE + "GETPOINTS" + RESET);
        System.out.println("Prints out all the scores of the players.");
        System.out.println(PURPLE + "GETTRAINS" + RESET);
        System.out.println("Prints out the number of trains you currently have.");
        System.out.println(PURPLE + "VIEWMAP" + RESET);
        System.out.println("Views a portion of the map.");
        System.out.println(PURPLE + "PRINTLOG" + RESET);
        System.out.println("Prints out the last number of log entries.");
    }
}
