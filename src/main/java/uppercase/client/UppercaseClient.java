package uppercase.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/** Connects, sends each line typed on stdin, and prints the server's reply. */
public class UppercaseClient {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8084;

        Socket socket = new Socket(host, port);
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String line;
            System.out.println("Waiting to receive user input...");
            while ((line = stdin.readLine()) != null) {
                out.println(line);                    // send one line
                System.out.println("Sent request: " + line);
                String response = in.readLine(); // blocks until the reply arrives
                System.out.println("Response: " + response);
            }
        } finally {
            socket.close();                           // also closes the socket's streams
        }
    }
}
