package com.github.leftisttachyon.ticket2ride.game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

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
     * Creates a new {@link Board} given the map structure.
     * @param railways the {@link Railway}s to create the map with.
     */
    public Board(Collection<Railway> railways) {
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
    }

    /**
     * Populates the card stack and the visible side cards.
     */
    private void populateCards() {
        for (int i = 0; i < 20; i++) {
            cardStack.push(Color.WHITE);
            cardStack.push(Color.BLUE);
            cardStack.push(Color.GREEN);
            cardStack.push(Color.YELLOW);
            cardStack.push(Color.ORANGE);
            cardStack.push(Color.RED);
            cardStack.push(Color.PINK);
            cardStack.push(Color.BLACK);

            if (i % 2 == 0) {
                cardStack.push(Color.RAINBOW);
            }
        }

        Collections.shuffle(cardStack);
        for (int i = 0; i < sideCards.length; i++) {
            sideCards[i] = cardStack.pop();
        }
    }
}
