package com.cinemacart;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;

public class Main {

    private static UserRepository userRepository; // Create an instance of the UserRepository class to interact w
    private static SessionManager sessionManager = new SessionManager();

    private static void initFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json"); // Path to the service account key file for Firebase authentication
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount)) // Set credentials for Firebase using the service account key file
            .build();
        FirebaseApp.initializeApp(options); // Initialize the Firebase application with the specified options
    }

    public static void main(String[] args) {

        try {
            initFirebase(); // Initialize Firebase and Firestore database connection
            userRepository = new UserRepository(); // Initialize the UserRepository instance to manage user accounts in the database
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            server.createContext("/", new LoginHandler(userRepository, sessionManager)); // Create a context for handling login requests using the LoginHandler class
            server.createContext("/booking", new BookingHandler(sessionManager, new BookingRepository())); // Create a context for handling booking requests using the BookingHandler class
            server.setExecutor(null);
            server.start();

            System.out.println("Server is running on port 8000");
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
            }
        }
    }