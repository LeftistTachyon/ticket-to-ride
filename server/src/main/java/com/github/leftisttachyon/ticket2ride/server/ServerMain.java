package com.github.leftisttachyon.ticket2ride.server;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class ServerMain {

    /**
     * The main method; the entry point
     *
     * @param args the command line arguments
     * @throws IOException if something goes wrong
     */
    public static void main(String[] args) throws IOException {
        log.info("The ticket to ride server is running.");

        try (ServerSocket listener = new ServerSocket(9001)) {
            while (true) {
                Socket socket = listener.accept();

                MiniServer h = new MiniServer(socket);

                new Thread(h::go).start();
            }
        } catch (BindException be) {
            log.error("Cannot start server", be);
            JOptionPane.showMessageDialog(null, "Cannot start server",
                    be.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
