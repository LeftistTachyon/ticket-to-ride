package com.github.leftisttachyon.ticket2ride.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private String name;
    /**
     * A {@link HashMap} that contains all of the cards that this player has.
     */
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
     * A {@link List} of {@link ActionListener}s that listen to actions related to this {@link Player}
     */
    @Getter(AccessLevel.NONE)
    private List<ActionListener> listeners;
    /**
     * The amounts of points this player has
     */
    private int points = 0;
    /**
     * The amount of unused trains that this {@link Player} has.
     */
    private int trains = 4;
    /**
     * The turn number associated with this {@link Player}
     */
    @Setter
    private int turn = -1;
    /**
     * The maximum number of cards this {@link Player} can return now
     */
    @Getter(AccessLevel.NONE)
    private int returnAllowance = 0;

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
        listeners = new LinkedList<>();
    }

    /**
     * Adds the given number of points to this {@link Player}.
     *
     * @param num the number of points to award
     */
    public void addPoints(int num) {
        points += num;

        notifyListeners("POINTS " + num);
    }

    /**
     * Adds the given card to this person's hand.
     *
     * @param cardColor the color of the card to add to this person's hand
     */
    public void addCard(Color cardColor) {
        if (cardColor == Color.NONE) {
            throw new IllegalArgumentException("Cannot add a \"NONE\" card to a player's hand");
        } else {
            cards.put(cardColor, cards.get(cardColor) + 1);

            notifyListeners("CARD ADD " + cardColor);
        }
    }

    /**
     * Subtracts the given {@link Map} from the internal one.
     *
     * @param toRemove the amount of cards to remove
     */
    public void removeCards(Map<Color, Integer> toRemove) {
        for (Map.Entry<Color, Integer> entry : toRemove.entrySet()) {
            removeCards(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Adds a {@link Railway} to the collection of owned {@link Railway}s
     *
     * @param railway the {@link Railway} to add to the owned collection of {@link Railway}s
     */
    public void addRailway(Railway railway) {
        ownedRailways.add(railway);

        Set<String> temp = new HashSet<>(railway.getDestinations());
        for (int i = network.size() - 1; i >= 0; i--) {
            Set<String> tempList = network.get(i);
            if (railway.containsEndpoint(tempList)) {
                network.remove(i);
                temp.addAll(tempList);
            }
        }

        network.add(temp);

        notifyListeners("RAIL ADD " + railway.toMessageString());
    }

    /**
     * Removes a {@link Railway} from the collection of owned {@link Railway}s
     *
     * @param railway the {@link Railway} to remove from the owned collection of {@link Railway}s
     */
    public void removeRailway(Railway railway) {
        ownedRailways.remove(railway);

        regenerateNetwork();

        notifyListeners("RAIL REMOVE " + railway.toMessageString());
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

        notifyListeners("ROUTE ADD " + route.toMessageString());
    }

    /**
     * Removes the given {@link Route} from the list of owned {@link Route}s
     *
     * @param route the {@link Route} to remove
     * @return whether the operation was successful
     */
    public boolean removeRoute(Route route) {
        if (returnAllowance > 0) {
            returnAllowance--;
        } else return false;

        if (routes.remove(route)) {
            notifyListeners("ROUTE REMOVE " + route.toMessageString());
            return true;
        } else return false;
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
     * Determines whether the given {@link Route} is completed.
     *
     * @param route the {@link Route} to evaluate
     * @return whether the given {@link Route} is completed
     */
    public boolean isCompleted(Route route) {
        for (Set<String> set : network) {
            if (route.containsEndpoint(set)) return true;
        }

        return false;
    }

    /**
     * Removes the given number of trains from this {@link Player}'s stockpile.
     *
     * @param num the number of trains to remove
     */
    public void removeTrains(int num) {
        trains -= num;

        notifyListeners("REMOVE-TRAINS " + num);
    }

    /**
     * Gets the number of cards this {@link Player} has.
     *
     * @return the number of cards this {@link Player} has
     */
    public int getNumCards() {
        int output = 0;
        for (int num : cards.values()) {
            output += num;
        }

        return output;
    }

    /**
     * Gets the most amount of contiguous trains this {@link Player} has.
     *
     * @return the most amount of contiguous trains
     */
    public int getLongestConnection() {
        HashMap<Set<String>, Integer> temp = new HashMap<>();
        outer:
        for (Railway r : ownedRailways) {
            for (Set<String> s : temp.keySet()) {
                if (s.contains(r.getDest1())) {
                    s.add(r.getDest2());
                    temp.put(s, temp.get(s) + r.getLength());
                    continue outer;
                } else if (s.contains(r.getDest2())) {
                    s.add(r.getDest1());
                    temp.put(s, temp.get(s) + r.getLength());
                    continue outer;
                }
            }

            // doesn't exist yet, create new one
            Set<String> s = new HashSet<>();
            s.add(r.getDest1());
            s.add(r.getDest2());
            temp.put(s, r.getLength());
        }

        int largest = -1;
        for (int i : temp.values()) {
            if (i > largest) {
                largest = i;
            }
        }

        return largest;
    }

    /**
     * Increments the return allowance.
     */
    public void incrementReturnAllowance() {
        returnAllowance++;
    }

    /**
     * Clears the return allowance.
     */
    public void clearReturnAllowance() {
        returnAllowance = 0;
    }

    /**
     * Returns whether this player is allowed to return a card or not.
     *
     * @return whether this player is allowed to return a card or not
     */
    public boolean canReturnCard() {
        return returnAllowance > 0;
    }

    // listener code

    /**
     * Adds an {@link ActionListener} to the internal {@link List} of listeners that is listening to this object.
     *
     * @param listener the {@link ActionListener} to add
     */
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes an {@link ActionListener} from the internal {@link List} of listeners that is listening to this object.
     *
     * @param listener the {@link ActionListener} to add
     */
    public void removeActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    /**
     * Clears all {@link ActionListener}s from this object.
     */
    public void clearActionListeners() {
        listeners.clear();
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
     * Removes the given number of color cards from this {@link Player}'s hand.
     *
     * @param key the type of card to remove
     * @param num the number of cards to remove
     */
    void removeCards(Color key, int num) {
        cards.put(key, Math.max(0, cards.get(key) - num));

        notifyListeners("CARDS REMOVE " + key + " " + num);
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

    /**
     * Notifies the associated listeners of the given message
     *
     * @param message the message to send to the listeners
     */
    private void notifyListeners(String message) {
        if (!listeners.isEmpty()) {
            ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, message);
            for (ActionListener listener : listeners) {
                listener.actionPerformed(evt);
            }
        }
    }
}
