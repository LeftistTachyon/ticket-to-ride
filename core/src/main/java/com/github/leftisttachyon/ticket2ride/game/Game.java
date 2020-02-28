package com.github.leftisttachyon.ticket2ride.game;

import com.github.leftisttachyon.ticket2ride.game.action.TurnChangeEvent;
import com.github.leftisttachyon.ticket2ride.game.action.TurnChangeListener;
import lombok.AccessLevel;
import lombok.Getter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that represents all the aspects of the game.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
@Getter
public class Game {
    /**
     * The internally stored board.
     */
    private final Board board;
    /**
     * An array that stores the {@link Player}s involved with this game.
     */
    private Player[] players;
    /**
     * A {@link List} of {@link TurnChangeListener}s that are notified when turns change.
     */
    @Getter(AccessLevel.NONE)
    private List<TurnChangeListener> turnChangeListeners;
    /**
     * A {@link List} of {@link ActionListener}s that are notified when actions occur.
     */
    @Getter(AccessLevel.NONE)
    private List<ActionListener> actionListeners;
    /**
     * An integer that stores whose turn it is
     */
    private int turn = 0;
    /**
     * An integer that stores how many cards the current player can draw
     */
    @Getter(AccessLevel.NONE)
    private int drawPower = 2;

    /**
     * Creates a new {@link Game} object.
     *
     * @param board      the {@link Board} object to create a {@link Game} with.
     * @param numPlayers the number of {@link Player}s to include in this game
     */
    public Game(Board board, int numPlayers) {
        this.board = board;

        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(null);
        }

        turnChangeListeners = new LinkedList<>();
        actionListeners = new LinkedList<>();
    }

    /**
     * Makes the current player pick up the given card from the side pile.<br>
     * If the move is invalid, then false is returned and nothing changes.
     *
     * @param idx the index of the card to pick
     * @return whether the operation was successful
     */
    public boolean pickCard(int idx) {
        Color c = board.pickCard(idx, drawPower);
        if (c == null) return false;

        players[turn].addCard(c);

        if (c == Color.RAINBOW) {
            drawPower -= 2;
        } else {
            drawPower--;
        }

        notifyAction("PICK " + c);

        if (drawPower == 0) {
            advanceTurn();
        }

        return true;
    }

    /**
     * Makes the current player pick up a card from the high pile.
     */
    public void pickRandom() {
        Color c = board.pickRandom();
        players[turn].addCard(c);

        notifyAction("PICK RANDOM");

        if (--drawPower == 0) {
            advanceTurn();
        }
    }

    /**
     * Makes the current player draw three route cards.
     */
    public void drawRoutes() {
        for (Route r : board.getRoutes(3)) {
            players[turn].addRoute(r);
        }

        notifyAction("DRAWROUTES");

        advanceTurn();
    }

    /**
     * Puts a {@link Route} card back to the internally stored {@link java.util.Stack}.
     *
     * @param p     the {@link Player} to remote the {@link Route} from
     * @param route the {@link Route} to put back
     */
    public boolean returnRoute(Player p, Route route) {
        if (!p.removeRoute(route)) {
            return false;
        }
        board.addRoute(route);

        notifyAction("RETURNROUTE " + p.getName());
        return true;
    }

    // TODO: have a way for the players to claim railways
    public void claimRailway(Railway railway, int rainbows) {
        // check if it's doable
        // do it
    }
    // TODO: starting the game
    // TODO: ending the game

    /**
     * Adds an {@link TurnChangeListener} to the internally stored {@link List}.
     *
     * @param listener the {@link TurnChangeListener} to add
     */
    public void addTurnChangeListener(TurnChangeListener listener) {
        turnChangeListeners.add(listener);
    }

    /**
     * Removes an {@link TurnChangeListener} from the internal {@link List}.
     *
     * @param listener the {@link TurnChangeListener} to remove
     */
    public void removeTurnChangeListener(TurnChangeListener listener) {
        turnChangeListeners.remove(listener);
    }

    /**
     * Clears all {@link TurnChangeListener}s from this object.
     */
    public void clearTurnChangeListeners() {
        turnChangeListeners.clear();
    }

    /**
     * Adds an {@link ActionListener} to the internally stored {@link List}.
     *
     * @param listener the {@link ActionListener} to add
     */
    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    /**
     * Removes an {@link ActionListener} from the internal {@link List}.
     *
     * @param listener the {@link ActionListener} to remove
     */
    public void removeActionListener(ActionListener listener) {
        actionListeners.remove(listener);
    }

    /**
     * Clears all {@link ActionListener}s from this object.
     */
    public void clearActionListeners() {
        actionListeners.clear();
    }

    /**
     * Advances turns.
     */
    private void advanceTurn() {
        drawPower = 2;
        turn = (turn + 1) % players.length;

        if (!turnChangeListeners.isEmpty()) {
            TurnChangeEvent evt = new TurnChangeEvent(this, ActionEvent.ACTION_PERFORMED, "Turn changed", turn);
            for (TurnChangeListener turnChangeListener : turnChangeListeners) {
                turnChangeListener.turnChanged(evt);
            }
        }
    }

    /**
     * Notifies {@link ActionListener}s that something happened
     *
     * @param message the message to relay
     */
    private void notifyAction(String message) {
        if (!actionListeners.isEmpty()) {
            ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, message);
            for (ActionListener listener : actionListeners) {
                listener.actionPerformed(evt);
            }
        }
    }
}
