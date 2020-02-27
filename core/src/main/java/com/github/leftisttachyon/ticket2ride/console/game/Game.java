package com.github.leftisttachyon.ticket2ride.console.game;

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
     * An array that stores the {@link Player}s involved with this game.
     */
    private Player[] players;
    /**
     * An integer that stores whose turn it is
     */
    private int turn = 0;

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
            players[i] = new Player();
        }
    }
}
