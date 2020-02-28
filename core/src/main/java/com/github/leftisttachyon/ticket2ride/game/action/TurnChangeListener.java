package com.github.leftisttachyon.ticket2ride.game.action;

/**
 * An interface that outlines a class that listens to changes in turns.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public interface TurnChangeListener {
    /**
     * Fired when a turn is changed.
     *
     * @param evt an object that represents the action performed
     */
    void turnChanged(TurnChangeEvent evt);
}
