package com.github.leftisttachyon.ticket2ride.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * A class that represents a player in the game.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
@Getter
public class Player {
    /**
     * The name of this player
     */
    @Setter
    private final String name;
    /**
     * A {@link HashMap} that contains all of the cards that this player has.
     */
    @Getter(AccessLevel.NONE)
    private HashMap<Color, Integer> cards;
    /**
     * A {@link List} of all owned {@link Railway}s.
     */
    private Set<Railway> ownedRailways;
    /**
     * A {@link List} of all {@link Route}s that this {@link Player} is working towards.
     */
    private List<Route> routes;
    /**
     * A {@link List} that stores the networks that this {@link Player} has connections to
     */
    @Getter(AccessLevel.NONE)
    private List<Set<String>> network;
    /**
     * The amounts of points this player has
     */
    private int points = 0;
    /**
     * The amount of unused trains that this {@link Player} has.
     */
    private int trains = 45;

    /**
     * Creates a new {@link Player}
     *
     * @param name the name to use for this {@link Player}
     */
    public Player(String name) {
        this.name = name;

        cards = new HashMap<>();
        for (Color c : Color.values()) {
            if (c != Color.NONE) cards.put(c, 0);
        }

        ownedRailways = new HashSet<>();
        routes = new LinkedList<>();
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
        switch (cardColor) {
            case NONE:
                throw new IllegalArgumentException("Cannot add a \"NONE\" card to a player's hand");
            case RAINBOW:
                throw new IllegalArgumentException("Cannot add a \"RAINBOW\" card to a player's hand");
            default:
                cards.put(cardColor, cards.get(cardColor) + 1);
        }
    }

    /**
     * Subtracts the given {@link Map} from the internal one.
     *
     * @param toRemove the amount of cards to remove
     */
    public void removeCards(Map<Color, Integer> toRemove) {
        for (Map.Entry<Color, Integer> entry : toRemove.entrySet()) {
            Color key = entry.getKey();
            cards.put(key, Math.max(0, cards.get(key) - entry.getValue()));
        }
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
     * @return whether the operation was successful
     */
    public boolean removeRoute(Route route) {
        return routes.remove(route);
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
     * Determines whether this {@link Player} has at least the given number of the given card type.
     *
     * @param type the type of the card to look for
     * @param num  the number to have at least of in order to return {@code true}
     * @return whether the criterion are met
     */
    boolean hasCards(Color type, int num) {
        return cards.get(type) >= num;
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
