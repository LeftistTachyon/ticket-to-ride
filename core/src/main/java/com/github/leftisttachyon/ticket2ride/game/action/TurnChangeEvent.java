package com.github.leftisttachyon.ticket2ride.game.action;

import lombok.Getter;

import java.awt.event.ActionEvent;

/**
 * A class that represents an event that occurs after turns are exchanged.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
@Getter
public class TurnChangeEvent extends ActionEvent {
    /**
     * The number for the new turn.
     */
    private final int newTurn;

    /**
     * Creates a new {@link TurnChangeEvent}
     *
     * @param source  the source of the event
     * @param id      the ID for this event
     * @param command a {@link String} associated with this event
     * @param newTurn the player number of the person whose turn it is now
     */
    public TurnChangeEvent(Object source, int id, String command, int newTurn) {
        super(source, id, command);

        this.newTurn = newTurn;
    }
}
