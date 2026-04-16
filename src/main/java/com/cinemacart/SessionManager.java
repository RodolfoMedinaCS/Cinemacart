package com.cinemacart;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * a. SessionManager
 * b. Date created: 
 * c. Author: Winter Tomas
 * 
 * d. The primary method of this class is to manage sessions for users when they login.
 *  Sessions are what allow a user to access account features such as booking, viewing booking history, and managing their account details. 
 *  Without a session, logging in becomes meaningless, as the user would not be able to access any of the features that require authentication. 
 *  Session tokens are stored as a map of sessionToken to email, allowing for easy retrieval of the user's email when they make requests that require authentication.
 *  Session tokens are issued upon logging in and expires when the user logs out.
 * 
 * e. Methods:
 * 
 * generateSessionToken - This method generates a unique session token for a user upon login, it takes in the user's email and creates a new session token using UUID.randomUUID().toString() and stores it in the sessions map with the email as the value.
 * @param email - The email of the user for whom the session token is being generated
 * @return - A unique session token that can be used to identify the user's session
 * 
 * getEmailByToken - This method takes in a session token and returns the email associated with that token, or null if the token is invalid (not found in the sessions map).
 * @param sessionToken - The session token for which to retrieve the associated email
 * @return - The email associated with the session token, or null if the token is invalid
 * 
 * validSession - Checks if a given session token is valid by checking if it exists in the sessions map. It returns true if the token is valid and false if it is not.
 * @param sessionToken - The session token to validate
 * @return - true if the session token is valid, false otherwise
 * 
 * invalidSession - Checks if a session token is valid and revokes it by removing it from the sessions map, which logs the user out and invalidates their session.
 * @param sessionToken - The session token to invalidate
 * 
 * f. Data structures:
 * A map is used to store the session tokens and their associated emails, allowing for efficient retrieval of the user's email when they make requests that require authentication. The session token is the key and the email is the value in the map.
*/    

public class SessionManager {

    private final Map<String, String> sessions = new HashMap<>();

    // Generate a session token for a user upon login
    public String generateSessionToken(String email) {
        String sessionToken = UUID.randomUUID().toString();
        sessions.put(sessionToken, email);
        return sessionToken;
    }

    // Returns email associated with the session token, or null if the token is invalid
    public String getEmailByToken(String sessionToken) {
        if (sessionToken == null) {
            return null;
        }
        return sessions.get(sessionToken);
    }

    // Returns true if the token exists in the sessions map, indicating that the session is valid
    public boolean validSession(String sessionToken) {
        return sessionToken != null && sessions.containsKey(sessionToken);
    }

    // Revoke session token when user logs out
    public void invalidSession(String sessionToken) {
        if (sessionToken != null) {
            sessions.remove(sessionToken);
        }
    }
}