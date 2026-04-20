package com.cinemacart;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import com.google.gson.Gson;

/**
 * It handles incoming HTTP requests related to searching movies
 * It also receives requests from the frontend, reads the JSON body, and processes
 * search queries using the Search class. The controller supports searching
 * by title or genre and returns the result as a JSON response.
 * If the query is empty, it returns all available movies. If no results are
 * found for a title search, it attempts to search by genre as a fallback.
 * It also manages request validation, error handling, and sets appropriate
 * HTTP response codes and headers for communication with the frontend.
 */
public class SearchController implements HttpHandler {

    static class SearchRequest {
        String action;
        String query;
    }

    public void handle(HttpExchange exchange) throws IOException {
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
        String response; // Variable to hold the response that will be sent back to the frontend
        int status; // Variable to hold the HTTP status code that will be sent back to the frontend, this is determined by the success or failure of the requested action
        Gson gson = new Gson(); // Create a new Gson instance to parse the JSON request body into a SearchRequest object
        SearchRequest req = gson.fromJson(requestBody, SearchRequest.class); // Convert the JSON request body into a SearchRequest object, allowing access the action and query fields from the request

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

        try {
            // handles search
            if ("search".equalsIgnoreCase(req.action)) {
                exchange.getResponseHeaders().set("Content-Type", "application/json");

                String q = (req.query == null) ? "" : req.query.trim();

                if (q.isEmpty()) {
                    // If the query is empty, return all movies
                    java.util.List<Movie> results = Search.search("", "", 0.0, "");
                    status = 200;
                    response = gson.toJson(results);
                } else {
                    java.util.List<Movie> results = Search.search(q, "", 0.0, "");
                    if(results.isEmpty()){
                        //if nothing found by title then try as genre
                        results = Search.search("", q, 0.0, "");
                    }
                    status = 200;
                    response = gson.toJson(results);
                }
            } else {
                response = "{\"error\":\"Invalid action\"}";
                status = 400;
            }

        } catch (Exception e) {
            response = "{\"error\":\"Server error\"}";
            status = 500;
            e.printStackTrace();
        }

        byte[] bytes = response.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}