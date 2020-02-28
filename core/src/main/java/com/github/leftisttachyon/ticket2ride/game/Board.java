package com.github.leftisttachyon.ticket2ride.game;

import java.util.*;

/**
 * A class that represents the entire playing board.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public class Board {
    /**
     * A {@link Stack} that represents the stack of train cards that sit there.
     */
    private Stack<Color> cardStack;
    /**
     * An array of {@link Color} objects that represent cards that just sit there next to the stack.
     */
    private Color[] sideCards;
    /**
     * An object that represents the map.
     */
    private HashMap<String, HashMap<String, List<Railway>>> map;
    /**
     * A {@link Stack} of {@link Route}s that can be taken.
     */
    private Stack<Route> routes;

    /**
     * Creates a new {@link Board} given the map structure.
     *
     * @param railways the {@link Railway}s to create the map with.
     * @param routes   the {@link Route}s associated with this map
     */
    public Board(Collection<Railway> railways, Collection<Route> routes) {
        cardStack = new Stack<>();
        sideCards = new Color[5];
        populateCards();

        map = new HashMap<>();
        for (Railway r : railways) {
            HashMap<String, List<Railway>> tempMap;
            String dest1 = r.getDest1(), dest2 = r.getDest2();

            if (map.containsKey(dest1)) {
                tempMap = map.get(dest1);
                if (tempMap.containsKey(dest2)) {
                    tempMap.get(dest2).add(r);
                } else {
                    LinkedList<Railway> list = new LinkedList<>();
                    list.add(r);
                    tempMap.put(dest2, list);
                }
            } else {
                tempMap = new HashMap<>();
                map.put(dest1, tempMap);

                LinkedList<Railway> list = new LinkedList<>();
                list.add(r);
                tempMap.put(dest2, list);
            }

            if (map.containsKey(dest2)) {
                tempMap = map.get(dest2);
                if (tempMap.containsKey(dest1)) {
                    tempMap.get(dest1).add(r);
                } else {
                    LinkedList<Railway> list = new LinkedList<>();
                    list.add(r);
                    tempMap.put(dest1, list);
                }
            } else {
                tempMap = new HashMap<>();
                map.put(dest2, tempMap);

                LinkedList<Railway> list = new LinkedList<>();
                list.add(r);
                tempMap.put(dest1, list);
            }
        }

        this.routes = new Stack<>();
        this.routes.addAll(routes);
        Collections.shuffle(this.routes);
    }

    /**
     * Creates a standard issue US-related {@link Board}.
     *
     * @return a {@link Board} with US data.
     */
    public static Board createUSBoard() {
        List<Railway> railways = new LinkedList<>();
        railways.add(new Railway(1, Color.NONE, "Vancouver", "Seattle"));
        railways.add(new Railway(1, Color.NONE, "Vancouver", "Seattle"));
        railways.add(new Railway(1, Color.NONE, "Portland", "Seattle"));
        railways.add(new Railway(1, Color.NONE, "Portland", "Seattle"));
        railways.add(new Railway(3, Color.NONE, "Vancouver", "Calgary"));
        railways.add(new Railway(4, Color.NONE, "Seattle", "Calgary"));
        railways.add(new Railway(6, Color.YELLOW, "Seattle", "Helena"));
        railways.add(new Railway(4, Color.NONE, "Calgary", "Helena"));
        railways.add(new Railway(6, Color.WHITE, "Calgary", "Winnipeg"));
        railways.add(new Railway(4, Color.BLUE, "Helena", "Winnipeg"));
        railways.add(new Railway(6, Color.BLUE, "Portland", "Salt Lake City"));
        railways.add(new Railway(5, Color.GREEN, "Portland", "San Francisco"));
        railways.add(new Railway(5, Color.PINK, "Portland", "San Francisco"));
        railways.add(new Railway(5, Color.ORANGE, "Salt Lake City", "San Francisco"));
        railways.add(new Railway(5, Color.WHITE, "Salt Lake City", "San Francisco"));
        railways.add(new Railway(3, Color.PINK, "Salt Lake City", "Helena"));
        railways.add(new Railway(3, Color.ORANGE, "Salt Lake City", "Las Vegas"));
        railways.add(new Railway(2, Color.NONE, "Los Angeles", "Las Vegas"));
        railways.add(new Railway(3, Color.PINK, "Los Angeles", "San Francisco"));
        railways.add(new Railway(3, Color.YELLOW, "Los Angeles", "San Francisco"));
        railways.add(new Railway(3, Color.NONE, "Los Angeles", "Phoenix"));
        railways.add(new Railway(6, Color.BLACK, "Los Angeles", "El Paso"));
        railways.add(new Railway(3, Color.NONE, "Phoenix", "El Paso"));
        railways.add(new Railway(5, Color.WHITE, "Phoenix", "Denver"));
        railways.add(new Railway(3, Color.RED, "Salt Lake City", "Denver"));
        railways.add(new Railway(3, Color.YELLOW, "Salt Lake City", "Denver"));
        railways.add(new Railway(3, Color.GREEN, "Helena", "Denver"));
        railways.add(new Railway(2, Color.NONE, "Santa Fe", "Denver"));
        railways.add(new Railway(3, Color.NONE, "Santa Fe", "Phoenix"));
        railways.add(new Railway(2, Color.NONE, "Santa Fe", "El Paso"));
        railways.add(new Railway(6, Color.GREEN, "Houston", "El Paso"));
        railways.add(new Railway(4, Color.RED, "Dallas", "El Paso"));
        railways.add(new Railway(5, Color.YELLOW, "Oklahoma City", "El Paso"));
        railways.add(new Railway(3, Color.BLUE, "Oklahoma City", "Santa Fe"));
        railways.add(new Railway(4, Color.RED, "Oklahoma City", "Denver"));
        railways.add(new Railway(4, Color.BLACK, "Kansas City", "Denver"));
        railways.add(new Railway(4, Color.ORANGE, "Kansas City", "Denver"));
        railways.add(new Railway(4, Color.PINK, "Omaha", "Denver"));
        railways.add(new Railway(5, Color.RED, "Omaha", "Helena"));
        railways.add(new Railway(6, Color.ORANGE, "Duluth", "Helena"));
        railways.add(new Railway(4, Color.BLACK, "Duluth", "Winnipeg"));
        railways.add(new Railway(6, Color.NONE, "Winnipeg", "Sault St. Marie"));
        railways.add(new Railway(3, Color.NONE, "Duluth", "Sault St. Marie"));
        railways.add(new Railway(2, Color.NONE, "Duluth", "Omaha"));
        railways.add(new Railway(2, Color.NONE, "Duluth", "Omaha"));
        railways.add(new Railway(1, Color.NONE, "Kansas City", "Omaha"));
        railways.add(new Railway(1, Color.NONE, "Kansas City", "Omaha"));
        railways.add(new Railway(2, Color.NONE, "Kansas City", "Oklahoma City"));
        railways.add(new Railway(2, Color.NONE, "Kansas City", "Oklahoma City"));
        railways.add(new Railway(2, Color.NONE, "Dallas", "Oklahoma City"));
        railways.add(new Railway(2, Color.NONE, "Dallas", "Oklahoma City"));
        railways.add(new Railway(1, Color.NONE, "Dallas", "Houston"));
        railways.add(new Railway(1, Color.NONE, "Dallas", "Houston"));
        railways.add(new Railway(2, Color.NONE, "New Orleans", "Houston"));
        railways.add(new Railway(3, Color.GREEN, "New Orleans", "Little Rock"));
        railways.add(new Railway(2, Color.NONE, "Dallas", "Little Rock"));
        railways.add(new Railway(2, Color.NONE, "Oklahoma City", "Little Rock"));
        railways.add(new Railway(2, Color.NONE, "Saint Louis", "Little Rock"));
        railways.add(new Railway(2, Color.BLUE, "Saint Louis", "Kansas City"));
        railways.add(new Railway(2, Color.PINK, "Saint Louis", "Kansas City"));
        railways.add(new Railway(2, Color.WHITE, "Saint Louis", "Chicago"));
        railways.add(new Railway(2, Color.GREEN, "Saint Louis", "Chicago"));
        railways.add(new Railway(4, Color.BLUE, "Omaha", "Chicago"));
        railways.add(new Railway(3, Color.RED, "Duluth", "Chicago"));
        railways.add(new Railway(6, Color.PINK, "Duluth", "Toronto"));
        railways.add(new Railway(2, Color.NONE, "Sault St. Marie", "Toronto"));
        railways.add(new Railway(5, Color.BLACK, "Sault St. Marie", "Montreal"));
        railways.add(new Railway(2, Color.NONE, "Boston", "Montreal"));
        railways.add(new Railway(2, Color.NONE, "Boston", "Montreal"));
        railways.add(new Railway(3, Color.BLUE, "New York", "Montreal"));
        railways.add(new Railway(3, Color.NONE, "Toronto", "Montreal"));
        railways.add(new Railway(4, Color.WHITE, "Toronto", "Chicago"));
        railways.add(new Railway(2, Color.NONE, "Toronto", "Pittsburgh"));
        railways.add(new Railway(3, Color.BLACK, "Chicago", "Pittsburgh"));
        railways.add(new Railway(3, Color.ORANGE, "Chicago", "Pittsburgh"));
        railways.add(new Railway(5, Color.GREEN, "Saint Louis", "Pittsburgh"));
        railways.add(new Railway(4, Color.YELLOW, "Nashville", "Pittsburgh"));
        railways.add(new Railway(2, Color.NONE, "Nashville", "Saint Louis"));
        railways.add(new Railway(3, Color.WHITE, "Nashville", "Little Rock"));
        railways.add(new Railway(1, Color.NONE, "Nashville", "Atlanta"));
        railways.add(new Railway(4, Color.YELLOW, "New Orleans", "Atlanta"));
        railways.add(new Railway(4, Color.ORANGE, "New Orleans", "Atlanta"));
        railways.add(new Railway(5, Color.RED, "New Orleans", "Miami"));
        railways.add(new Railway(5, Color.BLUE, "Atlanta", "Miami"));
        railways.add(new Railway(4, Color.PINK, "Charleston", "Miami"));
        railways.add(new Railway(2, Color.NONE, "Charleston", "Atlanta"));
        railways.add(new Railway(2, Color.NONE, "Charleston", "Raleigh"));
        railways.add(new Railway(2, Color.NONE, "Atlanta", "Raleigh"));
        railways.add(new Railway(2, Color.NONE, "Atlanta", "Raleigh"));
        railways.add(new Railway(3, Color.BLACK, "Nashville", "Raleigh"));
        railways.add(new Railway(2, Color.NONE, "Pittsburgh", "Raleigh"));
        railways.add(new Railway(2, Color.NONE, "Washington", "Raleigh"));
        railways.add(new Railway(2, Color.NONE, "Washington", "Raleigh"));
        railways.add(new Railway(2, Color.NONE, "Washington", "Pittsburgh"));
        railways.add(new Railway(2, Color.ORANGE, "Washington", "New York"));
        railways.add(new Railway(2, Color.BLACK, "Washington", "New York"));
        railways.add(new Railway(2, Color.WHITE, "Pittsburgh", "New York"));
        railways.add(new Railway(2, Color.GREEN, "Pittsburgh", "New York"));
        railways.add(new Railway(2, Color.YELLOW, "Boston", "New York"));
        railways.add(new Railway(2, Color.RED, "Boston", "New York"));

        List<Route> routes = new LinkedList<>();
        routes.add(new Route("Denver", "El Paso", 4));
        routes.add(new Route("Kansas City", "Houston", 5));
        routes.add(new Route("New York", "Atlanta", 6));
        routes.add(new Route("Chicago", "New Orleans", 7));
        routes.add(new Route("Calgary", "Salt Lake City", 7));
        routes.add(new Route("Helena", "Los Angeles", 8));
        routes.add(new Route("Duluth", "Houston", 8));
        routes.add(new Route("Sault St. Marie", "Nashville", 8));
        routes.add(new Route("Montreal", "Atlanta", 9));
        routes.add(new Route("Sault St. Marie", "Oklahoma City", 9));
        routes.add(new Route("Seattle", "Los Angeles", 9));
        routes.add(new Route("Chicago", "Santa Fe", 9));
        routes.add(new Route("Duluth", "El Paso", 10));
        routes.add(new Route("Toronto", "Miami", 10));
        routes.add(new Route("Portland", "Phoenix", 11));
        routes.add(new Route("Dallas", "New York City", 11));
        routes.add(new Route("Denver", "Pittsburgh", 11));
        routes.add(new Route("Winnipeg", "Little Rock", 11));
        routes.add(new Route("Winnipeg", "Houston", 12));
        routes.add(new Route("Boston", "Miami", 12));
        routes.add(new Route("Vancouver", "Santa Fe", 13));
        routes.add(new Route("Calgary", "Phoenix", 13));
        routes.add(new Route("Montreal", "New Orleans", 13));
        routes.add(new Route("Los Angeles", "Chicago", 16));
        routes.add(new Route("San Francisco", "Atlanta", 17));
        routes.add(new Route("Portland", "Nashville", 17));
        routes.add(new Route("Vancouver", "Montreal", 20));
        routes.add(new Route("Los Angeles", "Miami", 20));
        routes.add(new Route("Los Angeles", "New York City", 21));
        routes.add(new Route("Seattle", "New York", 22));

        return new Board(railways, routes);
    }

    /**
     * Picks the top card of the stack and removes it.
     *
     * @return the top card
     */
    public Color pickRandom() {
        return cardStack.pop();
    }

    /**
     * Picks a card on the visible four and replaces it with the top one from the stack.<br>
     * If "drawing power" is not sufficient, then {@code null} is returned.
     *
     * @param idx       the index of the card in the four
     * @param drawPower the amount of "drawing power" that this player still has
     * @return the picked card
     */
    public Color pickCard(int idx, int drawPower) {
        if (idx < 0 || idx >= sideCards.length) {
            throw new IllegalArgumentException("Invalid index");
        }

        Color c = sideCards[idx];
        if (c == Color.RAINBOW && drawPower < 2 || c != Color.RAINBOW && drawPower > 1) {
            return null;
        }

        sideCards[idx] = cardStack.pop();

        return c;
    }

    /**
     * Gets the first {@code num} {@link Route}s from the {@link Stack} of {@link Route}s.
     *
     * @param num the number of {@link Route}s to remove from the {@link Stack}
     * @return a {@link List} of {@link Route}s that were removed
     */
    public List<Route> getRoutes(int num) {
        List<Route> output = new LinkedList<>();
        while (num-- > 0 && !routes.isEmpty()) {
            output.add(routes.pop());
        }

        return output;
    }

    /**
     * Adds the given {@link Route} back to the internal {@link Stack} of {@link Route}s.
     *
     * @param toAdd the {@link Route} to add back
     */
    public void addRoute(Route toAdd) {
        routes.add((int) (Math.random() * routes.size()), toAdd);
    }

    /**
     * Returns a {@link List} containing all {@link Railway}s going out of the given city.
     *
     * @param start the city to find the routes coming out of
     * @return a {@link List} containing all {@link Railway}s going out of the given city
     */
    public List<Railway> getRailways(String start) {
//        map.get(start).values().stream().flatMap(Collection::stream).collect(Collectors.toList());

        Collection<List<Railway>> values = map.get(start).values();
        LinkedList<Railway> output = new LinkedList<>();

        for (List<Railway> value : values) {
            output.addAll(value);
        }

        return output;
    }

    /**
     * Gets a {@link List} of {@link String}s that represents cities that are adjacent to the given one.<br>
     * Note: will throw a {@link NullPointerException} if the city is not contained in this {@link Board}
     *
     * @param start the city to look for adjacents for
     * @return a {@link List} of {@link String}s of adjacent cities
     */
    public List<String> getAdjacents(String start) {
        LinkedList<String> output = new LinkedList<>();

        for (List<Railway> l : map.get(start).values()) {
            for (Railway r : l) {
                String s = r.getOtherDestination(start);
                if (s != null && !output.contains(s)) {
                    output.add(s);
                }
            }
        }

        return output;
    }

    /**
     * Claims a {@link Railway} equivalent to the given one for the given {@link Player} and eliminates all duplicates
     * if requested.
     *
     * @param r                the {@link Railway} to claim. Please note that the given object may not be found in the given
     *                         {@link Player}'s list of owned {@link Railway}s
     * @param claimer          the {@link Player} to claim the {@link Railway} for
     * @param removeDuplicates whether duplicate railways should be removed
     * @return whether the operation was successful
     */
    boolean claimRailway(Railway r, Player claimer, boolean removeDuplicates) {
        HashMap<String, List<Railway>> map1 = map.get(r.getDest1());
        if (map1 == null) return false;

        List<Railway> railways = map1.get(r.getDest2());
        label:
        {
            for (Railway rail : railways) {
                if (rail.getColor() == r.getColor()) {
                    rail.setClaimed(true);
                    claimer.addRailway(rail);
                    break label;
                }
            }

            return false;
        }

        if (removeDuplicates) {
            for (Railway rail : railways) {
                rail.setClaimed(true);
            }
        }

        return true;
    }

    /**
     * Returns a {@link Set} that contains all the cities
     *
     * @return a {@link Set} that contains all the cities
     */
    public Set<String> getCities() {
        return map.keySet();
    }

    /**
     * Populates the card stack and the visible side cards.
     */
    private void populateCards() {
        for (int i = 0; i < 12; i++) {
            cardStack.push(Color.WHITE);
            cardStack.push(Color.BLUE);
            cardStack.push(Color.GREEN);
            cardStack.push(Color.YELLOW);
            cardStack.push(Color.ORANGE);
            cardStack.push(Color.RED);
            cardStack.push(Color.PINK);
            cardStack.push(Color.BLACK);
        }
        for (int i = 0; i < 14; i++) {
            cardStack.push(Color.RAINBOW);
        }

        Collections.shuffle(cardStack);
        for (int i = 0; i < sideCards.length; i++) {
            sideCards[i] = cardStack.pop();
        }
    }
}
