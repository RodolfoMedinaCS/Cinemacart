package com.cinemacart;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;
import com.google.gson.Gson;
import java.util.UUID;

/**
 * a. LoginRegController
 * b. Date created: 
 * c. Author: Winter Tomas
 * 
 * d. LoginRegController class is responsible for handling both login and registration request from the login page. It uses the UserRepository class to interact with the user accounts stored in the database
 * and the SessionManager class to generate session tokens to manage user sessions. The handler processes incoming HTTP requests, extracts the relevant information (action type, email, password) and performs the approrpriate actions based
 * It also handles logout requests by invalidating the user's session token. This handler sends appropriate HTTP responses back to the client based on the outcome of the login, registration, or logout attempts, including status codes and messages. 
 * LoginRegController also creates the user account objects and saves them to the database upon registration.
 * 
 * e. Methods:
 * 
 * - LoginRegController - Constructor for the LoginRegController class, takes in a UserRepository and SessionManager instance to manage user accounts and sessions in the database
 * - handle - This void method is called when an HTTP request is received at the endpoint associated with this handler (HTTP requests for logging in and registering)
 * - Authorization - Used to represent the structure of the JSON data sent from the frontend for login, registration, and logout requests. It contains fields for action, email, password, and sessionToken
 * - LoginResponse - Used to represent the structure of the JSON response sent back to the client upon a successful login, it contains a message and a sessionToken field
 */


public class LoginRegController implements HttpHandler {
            
        private final UserRepository userRepository; // Create an instance of the UserRepository class to manage user accounts in the database
        private final SessionManager sessionManager; // Create an instance of the SessionManager class to generate user session tokens when they login

 /** 
 * LoginRegController - Constructor for the LoginRegController class, takes in a UserRepository and SessionManager instance to manage user accounts and sessions in the database
 * @param userRepository - An instance of the UserRepository class to manage user accounts in the database
 * @param sessionManager - An instance of the SessionManager class to generate user session tokens when they login
**/
        public LoginRegController(UserRepository userRepository, SessionManager sessionManager) {
            this.userRepository = userRepository;
            this.sessionManager = sessionManager;
        }    

 /** handle - This void method is called when an HTTP request is received at the endpoint associated with this handler (HTTP requests for logging in and registering). 
 * It processes the incoming request, validates the user's session with sessionToken, and performs the appropriate action: login, registration, or logout based on the "action" field in the request body. 
 * The method sends an HTTP response back to the client with the result of the login, registration, or logout operation.
 * @param exchange - An HttpExchange object that encapsulates the details of the incoming HTTP request and allows sending a response back to the client
 */       
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
                InputStream inputStream = exchange.getRequestBody(); // Get the request body from the incoming HTTP request, this contains the login or registration information sent from the frontend
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A"); // Use a Scanner to read the request body as a string, this allows us to easily parse the JSON data sent from the frontend
                String requestBody; // Declare a variable to hold the request body as a string
                if (scanner.hasNext()) { // Check if the scanner has any input, this is to prevent errors when trying to read an empty request body
                    requestBody = scanner.next();
                } else {
                    requestBody = "";
                }
                scanner.close();

                System.out.println("Received from frontend: " + requestBody);
            
                String response;
                int status;
                Gson gson = new Gson();
                Authorization req = gson.fromJson(requestBody, Authorization.class); // Allows us to access the action, email, and password fields from the request
                String action = req.action; // Get the action field from the request, this determines if user is trying to login or register
                String email = req.email; // Get the email field from the request, this is used to identify the user in the database
                String password = req.password; // Get the password field from the request

                // Registration / Logging in logic
                try {
                    if ("register".equalsIgnoreCase(action)) { // 
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
                    
                } else if ("login".equalsIgnoreCase(action)) {
                    UserAccount account = userRepository.findByEmail(email); // Find the user account in the database using the email, this is used to retrieve the stored password hash for comparison

                    if (account == null) {
                        status = 404;
                        response = "User not found";
                    } else if (BCrypt.checkpw(password, account.getPasswordHash())) { // Compare the input password with the stored password hash using BCrypt's checkpw method
                        String sessionToken = sessionManager.generateSessionToken(account.getEmail()); // Generate a session token for the user using the SessionManager class, this allows the user to access authenticated features of the application
                        status = 200;
                        response = gson.toJson(new LoginResponse("Login successful", sessionToken));
                    } else {
                        status = 401;
                        response = "Invalid credentials";
                    }

                } else if ("logout".equalsIgnoreCase(action)) {
                    sessionManager.invalidSession(req.sessionToken);
                    status = 200;
                    response = "Logout successful";
                    
                } else {
                    status = 400;
                    response = "Invalid action";
                }

                } catch (Exception e) {
                    status = 500;
                    response = "Server error: " + e.getMessage();
                }

                exchange.sendResponseHeaders(status, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                }    

                /** 
                * Authorization - Used to represent the structure of the JSON data sent from the frontend for login, registration, and logout requests. It contains fields for action, email, password, and sessionToken.
                **/
                static class Authorization {
                    String action;
                    String email;
                    String password;
                    String sessionToken; // Add sessionToken field to the Authorization class to handle logout requests
                }
                static class LoginResponse {
                    String message;
                    String sessionToken;

                /** 
                * LoginResponse - Used to represent the structure of the JSON response sent back to the client upon a successful login, it contains a message and a sessionToken field.
                **/
                    public LoginResponse(String message, String sessionToken) {
                        this.message = message;
                        this.sessionToken = sessionToken;
                    }
                }
            }