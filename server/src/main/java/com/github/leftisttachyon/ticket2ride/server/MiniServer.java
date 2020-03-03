package com.github.leftisttachyon.ticket2ride.server;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class that handles the client
 *
 * @author Jed Wang
 * @since 0.9.1
 */
@Slf4j
public class MiniServer {

    /**
     * A HashMap of every MiniServer created
     */
    private static final HashMap<String, MiniServer> SERVERS = new HashMap<>();

    /**
     * A Set of MiniServers which are busy
     */
    private static final Set<MiniServer> BUSY = new HashSet<>();

    /**
     * A reader coming from the user
     */
    private final BufferedReader in;

    /**
     * A writer going to the user
     */
    private final PrintWriter out;

    /**
     * The internal socket
     */
    public final Socket socket;

    /**
     * Whether the user is currently in a game
     */
    private boolean inGame;

    /**
     * The opponent, if any. If none, this is null.
     */
    private MiniServer opponent;

    /**
     * This user's name
     */
    private String name;

    /**
     * Creates a new MiniServer object with the given Socket
     *
     * @param s the Socket to use
     * @throws IOException if the in and out streams cannot be created
     */
    public MiniServer(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        inGame = false;
        opponent = null;
    }

    /**
     * Called to start interaction with the user
     */
    public void go() {
        try (out; in) {
            // Request a name from this client.  Keep requesting until
            // a name is submitted that is not already used.  Note that
            // checking for the existence of a name and adding the name
            // must be done while locking the set of names.
            while (true) {
                out.println("SUBMITNAME");
                // notify("SUBMITNAME", false);
                // notify(name, true);
                if ((name = in.readLine()) == null) {
                    return;
                }
                if ("".equals(name) || "null".equals(name)) {
                    continue;
                }
                synchronized (SERVERS) {
                    if (!SERVERS.containsKey(name)) {
                        for (Map.Entry<String, MiniServer> entry : SERVERS.entrySet()) {
                            entry.getValue().out.println("NEWCLIENTtrue " + name);
                            out.println("NEWCLIENTfalse " + entry.getKey());
                        }
                        SERVERS.put(name, this);
                        break;
                    }
                }
            }

            // Now that a successful name has been chosen, add the
            // socket's print writer to the set of all writers so
            // this client can receive broadcast messages.
            out.println("NAMEACCEPTED");
            // notify("NAMEACCEPTED", false);

            // Accept messages from this client and broadcast them.
            // Ignore other clients that cannot be broadcasted to.
            while (true) {
                String line = in.readLine();
                // notify(line, true);
                if (line == null) {
                    return;
                }

                // println("\"" + line + "\"");
                // handle input
                if (line.equals("PING")) {
                    out.println("PONG");
                } else if (line.startsWith("NLM")) {
                    String message = "NLM" + name + ": " + line.substring(3);
                    for (MiniServer h : SERVERS.values()) {
                        h.out.println(message);
                    }
                } else if (inGame) {
                    if (line.startsWith("EXIT")) {
                        // exit the match
                        exit();
                    } else {
                        opponent.out.println(line);
                    }
                } else {
                    if (line.startsWith("CHALLENGE_C")) {
                        // Challenging for a match
                        String toChallenge = line.substring(11);
                        if (toChallenge.equals(name)) {
                            continue;
                        }

                        if (SERVERS.containsKey(toChallenge)) {
                            SERVERS.get(toChallenge).out.println("CHALLENGE_C" + name);
                        } else {
                            log.warn("Opponent {} not found (149)", toChallenge);
                        }
                    } else if (line.startsWith("CHALLENGE_R")) {
                        // Challenge response: accept or reject
                        // Scanner temp = new Scanner(line.substring(11));
                        String[] data = line.substring(11).split(" ");

                        // Accepted!
                        String other = data[0];
                        if (SERVERS.containsKey(other)) {
                            MiniServer otherH = SERVERS.get(other);
                            if (Boolean.parseBoolean(data[1]) && !BUSY.contains(otherH)) {
                                startMatch(otherH);
                            } else {
                                otherH.out.println("CHALLENGE_Rfalse");
                            }
                        } else {
                            log.info("Opponent {} not found (161)", other);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.warn("An IOException was thrown", e);
        } finally {
            // This client is going down!  Remove its name and its print
            // writer from the sets, and close its socket.
            for (MiniServer h : SERVERS.values()) {
                h.out.println("REMOVECLIENT" + name);
            }

            if (opponent != null) {
                opponent.out.println("EXIT");
                opponent.inGame = false;
                for (MiniServer h : SERVERS.values()) {
                    h.out.println("FREE" + opponent.name);
                }
                opponent = null;
            }
            SERVERS.remove(name);

            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Exits the current game. Notifies the opponent of the exit as well.
     */
    private void exit() {
        inGame = false;
        BUSY.remove(this);

        for (MiniServer h : SERVERS.values()) {
            h.out.println("FREE" + name);
        }

        if (opponent != null) {
            opponent.out.println("EXIT");
            opponent.inGame = false;
            BUSY.remove(opponent);
            for (MiniServer h : SERVERS.values()) {
                h.out.println("FREE" + opponent.name);
            }

            opponent = null;
        }
    }

    /**
     * Starts a match between this client and the given one. Also takes care of
     * formalities like notifying other players that this server and the other
     * one are now busy.
     */
    private void startMatch(MiniServer other) {
        opponent = other;
        inGame = true;
        opponent.out.println("CHALLENGE_Rtrue");
        opponent.opponent = this;
        opponent.inGame = true;

        for (MiniServer h : SERVERS.values()) {
            h.out.println("BUSY" + name);
            h.out.println("BUSY" + opponent.name);
        }

        BUSY.add(opponent);
        BUSY.add(this);

        out.println("ST");
        opponent.out.println("ST");
    }
}
