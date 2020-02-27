package com.github.leftisttachyon.ticket2ride.console.game;

import lombok.Getter;

import java.util.*;

/**
 * A class that represents a player in the game.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public class Player {
    /**
     * A {@link HashMap} that contains all of the cards that this player has.
     */
    private HashMap<Color, Integer> cards;
    /**
     * A {@link List} of all owned {@link Railway}s.
     */
    @Getter
    private Set<Railway> ownedRailways;
    /**
     * A {@link List} of all {@link Route}s that this {@link Player} is working towards.
     */
    @Getter
    private Set<Route> routes;
    /**
     * A {@link List} that stores the networks that this {@link Player} has connections to
     */
    private List<Set<String>> network;
    /**
     * The amounts of points this player has
     */
    @Getter
    private int points = 0;
    /**
     * The amount of unused trains that this {@link Player} has.
     */
    @Getter
    private int trains = 45;

    /**
     * Creates a new {@link Player}
     */
    public Player() {
        cards = new HashMap<>();
        for (Color c : Color.values()) {
            if (c != Color.NONE) cards.put(c, 0);
        }

        ownedRailways = new HashSet<>();
        routes = new HashSet<>();
        network = new LinkedList<>();
    }

    /**
     * Adds the given number of points to this {@link Player}.
     *
     * @param num the number of points to award
     */
    public void addPoints(int num) {
        points += num;
    }

    /**
     * Adds the given card to this person's hand.
     *
     * @param cardColor the color of the card to add to this person's hand
     */
    public void addCard(Color cardColor) {
        if (cardColor == Color.NONE) {
            throw new IllegalArgumentException("Cannot add a \"NONE\" card to a player's hand");
        }

        cards.put(cardColor, cards.get(cardColor) + 1);
    }

    /**
     * Adds a {@link Railway} to the collection of owned {@link Railway}s
     *
     * @param railway the {@link Railway} to add to the owned collection of {@link Railway}s
     */
    public void addRailway(Railway railway) {
        ownedRailways.add(railway);

        Set<String> temp = railway.getDestinations();
        for (int i = network.size() - 1; i >= 0; i--) {
            Set<String> tempList = network.get(i);
            if (railway.containsEndpoint(tempList)) {
                network.remove(i);
                temp.addAll(tempList);
            }
        }

        network.add(temp);
    }

    /**
     * Removes a {@link Railway} from the collection of owned {@link Railway}s
     *
     * @param railway the {@link Railway} to remove from the owned collection of {@link Railway}s
     */
    public void removeRailway(Railway railway) {
        ownedRailways.remove(railway);

        regenerateNetwork();
    }

    /**
     * Determines whether the given {@link Railway} is owned by this {@link Player}.
     *
     * @param railway the {@link Railway} to check for
     * @return whether the given {@link Railway} is owned by this {@link Player}
     */
    public boolean ownsRailway(Railway railway) {
        return ownedRailways.contains(railway);
    }

    /**
     * Adds the given {@link Route} to the list of owned {@link Route}s
     *
     * @param route the {@link Route} to add
     */
    public void addRoute(Route route) {
        routes.add(route);
    }

    /**
     * Removes the given {@link Route} from the list of owned {@link Route}s
     *
     * @param route the {@link Route} to remove
     */
    public void removeRoute(Route route) {
        routes.remove(route);
    }

    /**
     * Determines whether the given {@link Route} is in the internally stored collection of {@link Route}s
     *
     * @param route the {@link Route} to check for
     * @return whether the given {@link Route} is stored in this {@link Player}
     */
    public boolean hasRoute(Route route) {
        return routes.contains(route);
    }

    /**
     * Removes the given number of trains from this {@link Player}'s stockpile.
     *
     * @param num the number of trains to remove
     */
    public void removeTrains(int num) {
        trains -= num;
    }

    /**
     * Recreates the {@code network} variable to ensure its accuracy.
     */
    private void regenerateNetwork() {
        network.clear();

        for (Railway r : ownedRailways) {
            Set<String> temp = r.getDestinations();
            for (int i = network.size() - 1; i >= 0; i--) {
                Set<String> tempList = network.get(i);
                if (r.containsEndpoint(tempList)) {
                    network.remove(i);
                    temp.addAll(tempList);
                }
            }

            network.add(temp);
        }
    }
}
