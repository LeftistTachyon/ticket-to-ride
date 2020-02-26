package com.github.leftisttachyon.ticket2ride.game;

/**
 * A class that represents all the aspects of the game.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
public class Game {
    /**
     * The internally stored board.
     */
    private final Board board;

    /**
     * Creates a new {@link Game} object.
     *
     * @param board the {@link Board} object to create a {@link Game} with.
     */
    public Game(Board board) {
        this.board = board;
    }
}
