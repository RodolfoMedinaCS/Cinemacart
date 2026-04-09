package com.cinemacart;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*  The primary method of this class is to manage sessions for users when they login.
 *  Sessions are what allow a user to access account features such as booking, viewing booking history, and managing their account details. 
 *  Without a session, logging in becomes meaningless, as the user would not be able to access any of the features that require authentication. 
 *  Session tokens are stored as a map of sessionToken to email, allowing for easy retrieval of the user's email when they make requests that require authentication.
 *  Session tokens are issued upon logging in and expires when the user logs out.
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