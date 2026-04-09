package com.cinemacart;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.List;
import com.google.gson.Gson;
import java.util.UUID;

/* BookingController class is responsible for handling booking related requests from the frontend. It uses the SessionManager class to validate user sessions and the BookingRepository class to interact with the bookings stored in the database.
 * BookingControllers processes are available to the user, these include creating new bookings, viewing booking history, and cancelling existing obokings. Bookings may only be deleted if they are cancelled first.
*/


    public class BookingController implements HttpHandler {
        
        private final SessionManager sessionManager;
        private final BookingRepository bookingRepository;
        
        // Constructor for the BookingController class, takes in a SessionManager and BookingRepository instance to manage user sessions and bookings in the database
        public BookingController(SessionManager sessionManager, BookingRepository bookingRepository) {
            this.sessionManager = sessionManager;
            this.bookingRepository = bookingRepository;
        }
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            InputStream inputStream = exchange.getRequestBody();
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String requestBody;

            if (scanner.hasNext()) {
                requestBody = scanner.next();
            } else {
                requestBody = "";
            }
            scanner.close();

            Gson gson = new Gson(); // Create a new Gson instance to parse the JSON request body into a BookingRequest object
            BookingRequest req = gson.fromJson(requestBody, BookingRequest.class); // Convert the JSON request body into a BookingRequest object, allowing access the action, sessionToken, movieId, bookingDate, bookingId, movieTitle, and purchaseDate fields from the request
            
            String response = "";
            int status;

            if (!sessionManager.validSession(req.sessionToken)) {
                System.out.println("Invalid session token");
                exchange.sendResponseHeaders(401, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }
            String email = sessionManager.getEmailByToken(req.sessionToken);

                try {
                    if ("book".equalsIgnoreCase(req.action)) {
                        // Creates a new booking and saves it to Firebase
                        String bookingId = UUID.randomUUID().toString();
                        Booking booking = new Booking(email, bookingId, req.movieId, req.bookingDate, "confirmed", req.movieTitle, req.bookingDate);
                        bookingRepository.save(booking);
                        status = 201;

                        System.out.println("Booking confirmed with ID: " + bookingId);

                    } else if ("viewBookings".equalsIgnoreCase(req.action)) {
                        List<Booking> bookings = bookingRepository.findByEmail(email);
                        status = 200;
                        response = gson.toJson(bookings);
                    
                    } else if ("cancel".equalsIgnoreCase(req.action)) {
                        //Updates the booking status to cancelled
                        bookingRepository.cancelBooking(req.bookingId);
                        status = 200;
                        System.out.println("Booking cancelled successfully");

                    } else {
                        status = 400;
                        System.out.println("Invalid action");
                    }
                
            } catch (Exception e) {
                status = 500;
                response = "Server error: " + e.getMessage();
            }

            // HTTP response to frontend sent in JSON format
            exchange.sendResponseHeaders(status, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

    static class BookingRequest {
        String action; // Action to perform: book, viewBookings, or cancel
        String sessionToken; // User's session token for authentication
        String movieId; // ID of the movie to book (required for booking)
        String bookingDate; // Date of the booking (required for booking)
        String bookingId; // ID of the booking to cancel (required for cancellation)
        String movieTitle; // Title of the movie to book (required for booking)
        String purchaseDate; // Date of the purchase (required for booking)
        }
    }
