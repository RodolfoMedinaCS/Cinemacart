package com.cinemacart;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

/**
* Prototype HTTP handler class, to be called within the various Controller class files to simplify in an effort to refactor a large amount of code
* UNTESTED
**/


public class HttpRequestController implements HttpHandler {

public void handle(HttpExchange exchange) throws IOException {
}

public String readBody(HttpExchange exchange) throws IOException {
    return new String(exchange.getRequestBody().readAllBytes());
}

public void sendHttpResponse(HttpExchange exchange, int status, String response) throws IOException {
    byte[] bytes = response.getBytes();
    exchange.sendResponseHeaders(status, bytes.length);
    OutputStream os = exchange.getResponseBody();
    os.write(bytes);
    os.close();
    }

public void addHeaders(HttpExchange exchange) {
    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
    exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
    exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }
}
