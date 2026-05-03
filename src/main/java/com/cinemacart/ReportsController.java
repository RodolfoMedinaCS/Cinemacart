package com.cinemacart;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class ReportsController extends HttpRequestController {

    private final SessionManager sessionManager;
    private final Firestore db;

    public ReportsController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.db = FirestoreClient.getFirestore();
    }

    public void handle(HttpExchange exchange) throws IOException {
        addHeaders(exchange);
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")){
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String requestBody = readBody(exchange);

        Gson gson = new Gson();
        ReportRequest req = gson.fromJson(requestBody, ReportRequest.class);

        String response = "";
        int status;

        if (!sessionManager.validSession(req.sessionToken)) {
            status = 401;
            exchange.sendResponseHeaders(status, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return;
        }

        String email = sessionManager.getEmailByToken(req.sessionToken);
        if (!"manager@cinemacart.com".equals(email)) {
            status = 403;
            exchange.sendResponseHeaders(status, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return;
        }

        try {
            List<QueryDocumentSnapshot> documents = db.collection("bookings").get().get().getDocuments();
            double totalRevenue = 0;
            int totalBookings = 0;
            int cancelledBookings = 0;
            Map<String, Integer> movieCount = new HashMap<>();
        
            for (int i = 0; i < documents.size(); i++) {
            QueryDocumentSnapshot doc = documents.get(i);

            String bookingDate = doc.getString("bookingDate");
            if (bookingDate == null) continue;

            String[] parts = bookingDate.split(" ");
            String docYear = parts[3];
            String docMonthName = parts[1];

            String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            String docMonth = "01";
            for (int m = 0; m < month.length; m++) {
                if (month[m].equals(docMonthName)) {
                    docMonth = String.format("%02d", m + 1);
                    break;
                }
            }
    
            if (!docMonth.equals(req.month) || !docYear.equals(req.year)) continue;
            totalBookings++;

            String bookingStatus = doc.getString("status");
            if ("cancelled".equalsIgnoreCase(bookingStatus)) {
                cancelledBookings++;
            } else {
                Double amount = doc.getDouble("amount");
                if (amount != null) totalRevenue += amount;
            }

            String movieTitle = doc.getString("movieTitle");
            if (movieTitle != null) {
                movieCount.put(movieTitle, movieCount.getOrDefault(movieTitle, 0) + 1);
            }
        }
        
            String mostPopularMovie = "N/A";
            int highestcount = 0;

            List<Map.Entry<String, Integer>> entries = new ArrayList<>(movieCount.entrySet());
            for (int i = 0; i < entries.size(); i++){
                if (entries.get(i).getValue() > highestcount) {
                highestcount = entries.get(i).getValue();
                mostPopularMovie = entries.get(i).getKey();
            }
        }    

        ReportResponse report = new ReportResponse(totalRevenue, totalBookings, cancelledBookings, req.month, req.year, mostPopularMovie);
        response = gson.toJson(report);
        status = 200;

    } catch (Exception e) {
        status = 500;
        response = "Server error: " + e.getMessage();
    }

        // HTTP response to frontend sent in JSON format
        sendHttpResponse(exchange, status, response);
    }

    static class ReportRequest {
        String sessionToken;
        String month;
        String year;
    }

    static class ReportResponse {
        double totalRevenue;
        int totalBookings;
        int cancelledBookings;
        String month;
        String year;
        String mostPopularMovie;

        public ReportResponse(double totalRevenue, int totalBookings, int cancelledBookings, String month, String year, String mostPopularMovie) {
            this.totalRevenue = totalRevenue;
            this.totalBookings = totalBookings;
            this.cancelledBookings = cancelledBookings;
            this.month = month;
            this.year = year;
            this.mostPopularMovie = mostPopularMovie;
        }
    }
}