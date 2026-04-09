package com.cinemacart;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.util.UUID;

public class Main {

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



            UserRepository userRepository = new UserRepository(); // Constructs a new UserRepository instance to manage user accounts in the database
            BookingRepository bookingRepository = new BookingRepository(); // Constructs a new BookingRepository instance to manage bookings in the database
            SessionManager sessionManager = new SessionManager(); // Constructs a new SessionManager instance to manage user sessions and generate session tokens when users login
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0); // Constructs an HTTP server on port 8000 for incoming http requests

            server.createContext("/", new LoginRegController(userRepository, sessionManager)); // Create a route for login and registration requests using the LoginController class
            server.createContext("/booking", new BookingController(sessionManager, bookingRepository)); // Create a route for  booking requests using the BookingController class
            server.setExecutor(null);
            server.start();

            // Test booking
            Booking testBooking = new Booking("1@email.com", UUID.randomUUID().toString(), "Test movie", "2026-04-07", "confirmed", "testMovie", "2026-04-07");
            bookingRepository.save(testBooking);
            System.out.println("Test booking saved to Firestore: " + testBooking.getBookingId());
            // end test

            System.out.println("Server is running on port 8000");
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
            }
        }


    }