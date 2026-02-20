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
import java.io.FileInputStream;
import org.mindrot.jbcrypt.BCrypt;

public class Main {

    public static void main(String[] args) {

        try {
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
                String response = "Hello";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }