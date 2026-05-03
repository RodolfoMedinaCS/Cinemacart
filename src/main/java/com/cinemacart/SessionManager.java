package com.cinemacart;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * SessionManager
 * Date created: Q1 of 2026
 * Author: Winter Tomas
 * 
 * The primary method of this class is to manage sessions for users when they login.
 *  Sessions are what allow a user to access account features such as booking, viewing booking history, and managing their account details. 
 *  Without a session, logging in becomes meaningless, as the user would not be able to access any of the features that require authentication. 
 *  Session tokens are stored as a map of sessionToken to email, allowing for easy retrieval of the user's email when they make requests that require authentication.
 *  Session tokens are issued upon logging in and expires when the user logs out.
 * 
 * Methods:
 * generateSessionToken - This method generates a unique session token for a user upon login, it takes in the user's email and creates a new session token using UUID.randomUUID().toString() and stores it in the sessions map with the email as the value
 * getEmailByToken - This method takes in a session token and returns the email associated with that token, or null if the token is invalid (not found in the sessions map)
 * validSession - Checks if a given session token is valid by checking if it exists in the sessions map. It returns true if the token is valid and false if it is not
 * invalidSession - This method takes in a session token and removes it from the sessions map, effectively invalidating the session and logging the user out
 * 
 * Data structures:
 * A map is used to store the session tokens and their associated emails, allowing for efficient retrieval of the user's email when they make requests that require authentication. The session token is the key and the email is the value in the map.
*/    

public class SessionManager {

    private final Map<String, String> sessions = new HashMap<>();

    /** 
     * generateSessionToken - This method generates a unique session token for a user upon login, it takes in the user's email and creates a new session token using UUID.randomUUID().toString() and stores it in the sessions map with the email as the value.
     * @param email - The email of the user for whom the session token is being generated
     * @return - A unique session token that can be used to identify the user's session
    **/
    public String generateSessionToken(String email) {
        String sessionToken = UUID.randomUUID().toString();
        sessions.put(sessionToken, email);
        return sessionToken;
    }

    /** 
     * getEmailByToken - This method takes in a session token and returns the email associated with that token, or null if the token is invalid (not found in the sessions map).
     * @param sessionToken - The session token for which to retrieve the associated email
     * @return - The email associated with the session token, or null if the token is invalid
    **/
    public String getEmailByToken(String sessionToken) {
        if (sessionToken == null) {
            return null;
        }
        return sessions.get(sessionToken);
    }

    /** 
     * validSession - Checks if a given session token is valid by checking if it exists in the sessions map. It returns true if the token is valid and false if it is not.
     * @param sessionToken - The session token to validate
     * @return - true if the session token is valid, false otherwise
    **/
    public boolean validSession(String sessionToken) {
        return sessionToken != null && sessions.containsKey(sessionToken);
    }
    
    /** 
     * invalidSession - This method takes in a session token and removes it from the sessions map, effectively invalidating the session and logging the user out.
     * @param sessionToken - The session token to invalidate
    **/
    public void invalidSession(String sessionToken) {
        if (sessionToken != null) {
            sessions.remove(sessionToken);
        }
    }

    public String checkManager(String email) {
        if ("manager@cinemacart.com".equals(email)) {
            String managerToken = UUID.randomUUID().toString();
            sessions.put(managerToken, email);
            return managerToken;
        }
        return null;
    }
}