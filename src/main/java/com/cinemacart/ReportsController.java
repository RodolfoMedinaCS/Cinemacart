package com.cinemacart;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

public class ReportsController implements HttpHandler {

    private final SessionManager sessionManager;
    private final Firestore db;

    public ReportsController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.db = FirestoreClient.getFirestore();
    }

    public void handle(Httpexchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Acess-Conntrol-Allow-Headers", "Content-Type");
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")){
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        InputStream inputStream = exchange.getRequestBody();
        Scanner scanner = new Scanner(inputStream);
        String requestBody = scanner.hasNext();
        scanner.close();

        Gson gson = new Gson();
        ReportRequest req = gson.fromJson(requestBody, ReportRequest.class);
    }
}