package com.github.leftisttachyon.ticket2ride.game;

import java.util.*;

import static com.github.leftisttachyon.ticket2ride.game.Color.*;

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
    private HashMap<String, HashMap<String, Railway>> map;
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
        sideCards = new Color[4];
        populateCards();

        map = new HashMap<>();
        for (Railway r : railways) {
            HashMap<String, Railway> tempMap;
            String dest1 = r.getDest1(), dest2 = r.getDest2();

            if (map.containsKey(dest1)) {
                map.get(dest1).put(dest2, r);
            } else {
                tempMap = new HashMap<>();
                map.put(dest1, tempMap);

                tempMap.put(dest2, r);
            }

            if (map.containsKey(dest2)) {
                map.get(dest2).put(dest1, r);
            } else {
                tempMap = new HashMap<>();
                map.put(dest2, tempMap);

                tempMap.put(dest1, r);
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
        railways.add(new Railway(1, NONE, "Vancouver", "Seattle"));
        railways.add(new Railway(1, NONE, "Vancouver", "Seattle"));
        railways.add(new Railway(1, NONE, "Portland", "Seattle"));
        railways.add(new Railway(1, NONE, "Portland", "Seattle"));
        railways.add(new Railway(3, NONE, "Vancouver", "Calgary"));
        railways.add(new Railway(4, NONE, "Seattle", "Calgary"));
        railways.add(new Railway(6, YELLOW, "Seattle", "Helena"));
        railways.add(new Railway(4, NONE, "Calgary", "Helena"));
        railways.add(new Railway(6, WHITE, "Calgary", "Winnipeg"));
        railways.add(new Railway(4, BLUE, "Helena", "Winnipeg"));
        railways.add(new Railway(6, BLUE, "Portland", "Salt Lake City"));
        railways.add(new Railway(5, GREEN, "Portland", "San Francisco"));
        railways.add(new Railway(5, PINK, "Portland", "San Francisco"));
        railways.add(new Railway(5, ORANGE, "Salt Lake City", "San Francisco"));
        railways.add(new Railway(5, WHITE, "Salt Lake City", "San Francisco"));
        railways.add(new Railway(3, PINK, "Salt Lake City", "Helena"));
        railways.add(new Railway(3, ORANGE, "Salt Lake City", "Las Vegas"));
        railways.add(new Railway(2, NONE, "Los Angeles", "Las Vegas"));
        railways.add(new Railway(3, PINK, "Los Angeles", "San Francisco"));
        railways.add(new Railway(3, YELLOW, "Los Angeles", "San Francisco"));
        railways.add(new Railway(3, NONE, "Los Angeles", "Phoenix"));
        railways.add(new Railway(6, BLACK, "Los Angeles", "El Paso"));
        railways.add(new Railway(3, NONE, "Phoenix", "El Paso"));
        railways.add(new Railway(5, WHITE, "Phoenix", "Denver"));
        railways.add(new Railway(3, RED, "Salt Lake City", "Denver"));
        railways.add(new Railway(3, YELLOW, "Salt Lake City", "Denver"));
        railways.add(new Railway(3, GREEN, "Helena", "Denver"));
        railways.add(new Railway(2, NONE, "Santa Fe", "Denver"));
        railways.add(new Railway(3, NONE, "Santa Fe", "Phoenix"));
        railways.add(new Railway(2, NONE, "Santa Fe", "El Paso"));
        railways.add(new Railway(6, GREEN, "Houston", "El Paso"));
        railways.add(new Railway(4, RED, "Dallas", "El Paso"));
        railways.add(new Railway(5, YELLOW, "Oklahoma City", "El Paso"));
        railways.add(new Railway(3, BLUE, "Oklahoma City", "Santa Fe"));
        railways.add(new Railway(4, RED, "Oklahoma City", "Denver"));
        railways.add(new Railway(4, BLACK, "Kansas City", "Denver"));
        railways.add(new Railway(4, ORANGE, "Kansas City", "Denver"));
        railways.add(new Railway(4, PINK, "Omaha", "Denver"));
        railways.add(new Railway(5, RED, "Omaha", "Helena"));
        railways.add(new Railway(6, ORANGE, "Duluth", "Helena"));
        railways.add(new Railway(4, BLACK, "Duluth", "Winnipeg"));
        railways.add(new Railway(6, NONE, "Winnipeg", "Sault St. Marie"));
        railways.add(new Railway(3, NONE, "Duluth", "Sault St. Marie"));
        railways.add(new Railway(2, NONE, "Duluth", "Omaha"));
        railways.add(new Railway(2, NONE, "Duluth", "Omaha"));
        railways.add(new Railway(1, NONE, "Kansas City", "Omaha"));
        railways.add(new Railway(1, NONE, "Kansas City", "Omaha"));
        railways.add(new Railway(2, NONE, "Kansas City", "Oklahoma City"));
        railways.add(new Railway(2, NONE, "Kansas City", "Oklahoma City"));
        railways.add(new Railway(2, NONE, "Dallas", "Oklahoma City"));
        railways.add(new Railway(2, NONE, "Dallas", "Oklahoma City"));
        railways.add(new Railway(1, NONE, "Dallas", "Houston"));
        railways.add(new Railway(1, NONE, "Dallas", "Houston"));
        railways.add(new Railway(2, NONE, "New Orleans", "Houston"));
        railways.add(new Railway(3, GREEN, "New Orleans", "Little Rock"));
        railways.add(new Railway(2, NONE, "Dallas", "Little Rock"));
        railways.add(new Railway(2, NONE, "Oklahoma City", "Little Rock"));
        railways.add(new Railway(2, NONE, "Saint Louis", "Little Rock"));
        railways.add(new Railway(2, BLUE, "Saint Louis", "Kansas City"));
        railways.add(new Railway(2, PINK, "Saint Louis", "Kansas City"));
        railways.add(new Railway(2, WHITE, "Saint Louis", "Chicago"));
        railways.add(new Railway(2, GREEN, "Saint Louis", "Chicago"));
        railways.add(new Railway(4, BLUE, "Omaha", "Chicago"));
        railways.add(new Railway(3, RED, "Duluth", "Chicago"));
        railways.add(new Railway(6, PINK, "Duluth", "Toronto"));
        railways.add(new Railway(2, NONE, "Sault St. Marie", "Toronto"));
        railways.add(new Railway(5, BLACK, "Sault St. Marie", "Montreal"));
        railways.add(new Railway(2, NONE, "Boston", "Montreal"));
        railways.add(new Railway(2, NONE, "Boston", "Montreal"));
        railways.add(new Railway(3, BLUE, "New York", "Montreal"));
        railways.add(new Railway(3, NONE, "Toronto", "Montreal"));
        railways.add(new Railway(4, WHITE, "Toronto", "Chicago"));
        railways.add(new Railway(2, NONE, "Toronto", "Pittsburgh"));
        railways.add(new Railway(3, BLACK, "Chicago", "Pittsburgh"));
        railways.add(new Railway(3, ORANGE, "Chicago", "Pittsburgh"));
        railways.add(new Railway(5, GREEN, "Saint Louis", "Pittsburgh"));
        railways.add(new Railway(4, YELLOW, "Nashville", "Pittsburgh"));
        railways.add(new Railway(2, NONE, "Nashville", "Saint Louis"));
        railways.add(new Railway(3, WHITE, "Nashville", "Little Rock"));
        railways.add(new Railway(1, NONE, "Nashville", "Atlanta"));
        railways.add(new Railway(4, YELLOW, "New Orleans", "Atlanta"));
        railways.add(new Railway(4, ORANGE, "New Orleans", "Atlanta"));
        railways.add(new Railway(5, RED, "New Orleans", "Miami"));
        railways.add(new Railway(5, BLUE, "Atlanta", "Miami"));
        railways.add(new Railway(4, PINK, "Charleston", "Miami"));
        railways.add(new Railway(2, NONE, "Charleston", "Atlanta"));
        railways.add(new Railway(2, NONE, "Charleston", "Raleigh"));
        railways.add(new Railway(2, NONE, "Atlanta", "Raleigh"));
        railways.add(new Railway(2, NONE, "Atlanta", "Raleigh"));
        railways.add(new Railway(3, BLACK, "Nashville", "Raleigh"));
        railways.add(new Railway(2, NONE, "Pittsburgh", "Raleigh"));
        railways.add(new Railway(2, NONE, "Washington", "Raleigh"));
        railways.add(new Railway(2, NONE, "Washington", "Raleigh"));
        railways.add(new Railway(2, NONE, "Washington", "Pittsburgh"));
        railways.add(new Railway(2, ORANGE, "Washington", "New York"));
        railways.add(new Railway(2, BLACK, "Washington", "New York"));
        railways.add(new Railway(2, WHITE, "Pittsburgh", "New York"));
        railways.add(new Railway(2, GREEN, "Pittsburgh", "New York"));
        railways.add(new Railway(2, YELLOW, "Boston", "New York"));
        railways.add(new Railway(2, RED, "Boston", "New York"));

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
     * Picks a card on the visible four and replaces it with the top one from the stack.
     *
     * @param idx the index of the card in the four
     * @return the picked card
     */
    public Color pickCard(int idx) {
        if (idx < 0 || idx >= sideCards.length) {
            throw new IllegalArgumentException("Invalid index");
        }

        Color c = sideCards[idx];
        sideCards[idx] = cardStack.pop();

        return c;
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
            cardStack.push(YELLOW);
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
