package uppercase.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/** Sends back every line it receives, converted to uppercase. One client at a time. */
public class UppercaseServer {
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8084;

        ServerSocket serverSocket = new ServerSocket(port);
        try {
            System.out.println("Listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();         // blocks until a client connects
                System.out.println("New connection!");
                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
                    String line;
                    System.out.println("Waiting to receive a request...");
                    while ((line = in.readLine()) != null) {   // blocks until a full line arrives
                        System.out.println("Received request: " + line);
                        out.println(line.toUpperCase());       // println flushes (autoflush = true)
                    }
                } catch (IOException e) {
                    System.err.println("Connection error: " + e.getMessage());
                } finally {
                    try {
                        socket.close();                        // also closes the socket's streams
                    } catch (IOException e) {
                        System.err.println("Close error: " + e.getMessage());
                    }
                }
            }
        } finally {
            serverSocket.close();
        }
    }
}
