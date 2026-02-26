package com.cinemacart;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import java.io.FileInputStream;
import org.mindrot.jbcrypt.BCrypt;

public class Main {

    private static Firestore db;

    private static void initFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json"); // Path to the service account key file for Firebase authentication

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount)) // Set credentials for Firebase using the service account key file
            .build();

        FirebaseApp.initializeApp(options); // Initialize the Firebase application with the specified options
        db = FirestoreClient.getFirestore(); // Get an instance of the Firestore database
    }
    public static void main(String[] args) {

        try {
            initFirebase(); // Initialize Firebase and Firestore database connection
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            
            server.createContext("/", new Handler());       
            server.setExecutor(null);
            server.start();

            System.out.println("Server is running on port 8000");
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
            }
        }

        static class Handler implements HttpHandler {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                // Allows two way communications
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }

                // Read received message
                InputStream inputStream = exchange.getRequestBody();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                String requestBody = scanner.hasNext() ? scanner.next() : "";
                scanner.close();

                // Print received message
                System.out.println("Received from frontend: " + requestBody);

                // Respond with Hello
                String response = "Hello";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }