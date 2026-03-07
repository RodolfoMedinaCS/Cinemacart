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
import com.google.gson.Gson;
import com.google.rpc.context.AttributeContext.Auth;
import java.util.UUID;

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

    static class Authorization {
        String action; // Variable that determines if user is logging in or registering, this is used to determine which method to call in the handler class
        String email; 
        String password;
    }

    private static UserRepository userRepository; // Create an instance of the UserRepository class to interact with the Firestore database for user account management
    
    public static void main(String[] args) {

        try {
            initFirebase(); // Initialize Firebase and Firestore database connection
            userRepository = new UserRepository(); // Initialize the UserRepository instance to manage user accounts in the database
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
            
                String response;
                int status;
                
                Gson gson = new Gson();
                Authorization req = gson.fromJson(requestBody, Authorization.class); // Allows us to access the action, email, and password fields from the request

                String action = req.action; // Get the action field from the request, this determines if user is trying to login or register
                String email = req.email; // Get the email field from the request, this is used to identify the user in the database
                String password = req.password; // Get the password field from the request

                // Registration / Logging in logic
                try {
                    if ("register".equalsIgnoreCase(action)) { // If the action is "register", call the register method in the handler class
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

                exchange.sendResponseHeaders(status, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
        }
    }
}