package com.github.leftisttachyon.ticket2ride.console;

import com.github.leftisttachyon.ticket2ride.game.Board;
import com.github.leftisttachyon.ticket2ride.game.Game;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The main class for this application.
 *
 * @author Jed Wang
 * @since 1.0.0
 */
@Slf4j
public class Main {
    /**
     * The main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            Board b = Board.createUSBoard();

            System.out.print("Enter the number of players: ");
            int players = Integer.parseInt(in.readLine());
            Game g = new Game(b, players);
            g.addActionListener(evt -> System.out.println(ConsoleColors.GREEN_BRIGHT + evt.getActionCommand() +
                    ConsoleColors.RESET));
            g.addTurnChangeListener(evt -> System.out.println(ConsoleColors.BLUE_BRIGHT + "Changed turn to " +
                    evt.getNewTurn() + "." + ConsoleColors.RESET));

            g.startGame();

            for (int i = 0; i < 10; i++) {
                int j = 0;

                while (!g.pickCard(j)) {
                    j++;
                }
            }
        } catch (IOException e) {
            log.warn("An IOException was thrown", e);
        }
    }

}
