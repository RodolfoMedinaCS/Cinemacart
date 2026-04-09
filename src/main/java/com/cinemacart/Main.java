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

    static class Authorization {
        String action; // Variable that determines if user is logging in or registering, this is used to determine which method to call in the handler class
        String email; 
        String password;
        String query;
    }

    private static UserRepository userRepository; // Create an instance of the UserRepository class to interact with the Firestore database for user account management
    
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


                // Read received message
                InputStream inputStream = exchange.getRequestBody();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                String requestBody = scanner.hasNext() ? scanner.next() : "";
                scanner.close();

                // Print received message
                System.out.println("Received from frontend: " + requestBody);

                // Respond with Hello
            
                String response;
                int status;

                Gson gson = new Gson();
                Authorization req = gson.fromJson(requestBody, Authorization.class); // Allows us to access the action, email, and password fields from the request

                // If the request body is empty or invalid JSON, req can be null
                if (req == null || req.action == null) {
                    response = "{\"error\":\"Missing action\"}";
                     status = 400;

                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    byte[] bytes = response.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                    exchange.sendResponseHeaders(status, bytes.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(bytes);
                    os.close();
                    return;
                }

                String action = req.action; // Get the action field from the request, this determines if user is trying to login or register
                String email = req.email; // Get the email field from the request, this is used to identify the user in the database
                String password = req.password; // Get the password field from the request
                String query = req.query; // Get the query field from the request

                try {
                    // handles search
                    if ("search".equalsIgnoreCase(action)) {
                        exchange.getResponseHeaders().set("Content-Type", "application/json");

                        String q = (query == null) ? "" : query.trim();

                        if (q.isEmpty()) {
                            status = 200;
                            response = "[]";
                        } else {
                            java.util.List<Movie> results = Search.search(q, "", 0.0, "");
                            status = 200;
                            response = gson.toJson(results);
                        }
                    }
                    // Registration / Logging in logic
                    else if ("register".equalsIgnoreCase(action)) { // If the action is "register", call the register method in the handler class
                    if (userRepository.exists(email)) {
                        status = 409;
                        response = "User already exists";
                    } else {
                        String userId = UUID.randomUUID().toString(); // Generate a unique user ID using UUID, this is used to identify the user in the database
                        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt()); // Hash the user's password using BCrypt, this is used to securely store the password in the database
                        UserAccount account = new UserAccount(userId, email, email, passwordHash);
                        userRepository.save(account); // Save the new user account to the database using the save method in the UserRepository class
                        
                        status = 201;
                        response = "User registered successfully";
                    }
                }

                else if ("login".equalsIgnoreCase(action)) {
                    UserAccount account = userRepository.findByEmail(email); // Find the user account in the database using the email, this is used to retrieve the stored password hash for comparison

                    if (account == null) {
                        status = 404;
                        response = "User not found";
                    } else if (BCrypt.checkpw(password, account.getPasswordHash())) { // Compare the input password with the stored password hash using BCrypt's checkpw method
                        status = 200;
                        response = "Login successful";
                    } else {
                        status = 401;
                        response = "Invalid credentials";
                    }
                    
                } else {
                    status = 400;
                    response = "Invalid action";
                }

                } catch (Exception e) {
                    status = 500;
                    response = "Server error: " + e.getMessage();
                }

                byte[] bytes = response.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(status, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
        }
    }
}
