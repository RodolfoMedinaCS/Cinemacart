package com.cinemacart;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;

public class CartController implements HttpHandler {

    private final SessionManager sessionManager;

    // to store each user's cart using their session token
    private final Map<String, Cart> carts = new HashMap<> ();

    public CartController(SessionManager sessionManager) {

        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //we allow the frontend to communicate with backend
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");


        //Handle preflight request( the browser ask permission before sending request)
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;

        }

        // To read the JSON request body from frontend
        InputStream inputStream = exchange.getRequestBody();
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String requestBody = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

            Gson gson = new Gson();
        CartRequest req = gson.fromJson(requestBody, CartRequest.class);

        //make  sure request has required fields
        if (req == null || req.action == null || req.sessionToken == null) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        // we check if user session is valid
        if (!sessionManager.validSession(req.sessionToken)) {
            exchange.sendResponseHeaders(401, -1);

            return;
        }

        String sessionToken = req.sessionToken;

        //Get existing cart or create a new one for this session
        Cart cart = carts.computeIfAbsent(sessionToken, k -> new Cart());

        String response;
        int status;

        try {


            // To return current cart data
            if ("getCart".equalsIgnoreCase(req.action)) {

                response = gson.toJson(cart);
                status = 200;

            }
            // update the  cart with new selections
            else if ("updateCart".equalsIgnoreCase(req.action)) {

                //Check required movie and showtime info
                if (req.movieId == null || req.movieTitle == null ||
                        req.selectedDate == null || req.selectedTime == null) {

                    status = 400;
                    response = "{\"error\":\"Missing required fields\"}";

                } else {

                    //So if seats not provided we initialize empty list
                    if (req.selectedSeats == null) {
                        req.selectedSeats = new ArrayList<>();
                    }

                    //Set movie and showtime details
                    cart.setMovieId(req.movieId);
                    cart.setMovieTitle(req.movieTitle);
                    cart.setSelectedDate(req.selectedDate);

                    cart.setSelectedTime(req.selectedTime);

                    // set selected seats and ticket counts
                    cart.setSelectedSeats(req.selectedSeats);
                    cart.setAdultCount(req.adultCount);
                    cart.setChildCount(req.childCount);
                    cart.setSeniorCount(req.seniorCount);

                    //making sure number of tickets matches selected seats
                    if (!cart.isTicketSeatMatch()) {
                        status = 400;
                        response = "{\"error\":\"Seats and tickets do not match\"}";
                    } else {
                        carts.put(sessionToken, cart);
                        status = 200;
                        response = "{\"message\":\"Cart updated\"}";
                    }
                }

            }
            //We clear all cart data for the user
            else if ("clearCart".equalsIgnoreCase(req.action)) {

                cart.clear();
                carts.put(sessionToken, cart);

                response = "{\"message\":\"Cart cleared\"}";
                status = 200;

            }
            // invalid action case
            else {
                status = 400;
                response = "{\"error\":\"Invalid action\"}";
            }

        } catch (Exception e) {

            status = 500;
            response = "{\"error\":\"Server error\"}";
        }

        byte[] bytes = response.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    //Structure  of the request coming from frontend
    static class CartRequest {
        String action;
        String sessionToken;
        Integer movieId;
        String movieTitle;
        String selectedDate;
        String selectedTime;

        List<String> selectedSeats;

        int adultCount;
        int childCount;
        int seniorCount;


    }
}