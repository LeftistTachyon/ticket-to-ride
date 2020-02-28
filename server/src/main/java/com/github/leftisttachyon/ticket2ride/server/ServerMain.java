package com.github.leftisttachyon.ticket2ride.server;

import javax.swing.*;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The main class; entry point of the application
 *
 * @author Jed Wang
 * @since 0.9.1
 */
public class ServerMain {

    /**
     * The main method; the entry point
     *
     * @param args the command line arguments
     * @throws IOException if something goes wrong
     */
    public static void main(String[] args) throws IOException {
        System.out.println("The ticket to ride server is running.");

        try (ServerSocket listener = new ServerSocket(9001)) {
            while (true) {
                Socket socket = listener.accept();

                MiniServer h = new MiniServer(socket);

                new Thread(h::go).start();
            }
        } catch (BindException be) {
            System.err.println("Cannot start server: " + be.getMessage());
            JOptionPane.showMessageDialog(null, "Cannot start server",
                    be.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
