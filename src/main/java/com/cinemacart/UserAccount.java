//Holds the users information, user ID, username, and email
package com.cinemacart;

import org.mindrot.jbcrypt.BCrypt;

/**
 * a. UserAccount
 * b. Date created: 
 * c. Author: Winter Tomas
 * 
 * d. The primary method of this class is to manage user accounts and their information.
 *  This includes storing and retrieving user details such as ID, username, email, and password hash.
 * 
 * e. Methods:
 * - UserAccount - Constructor for the UserAccount class, initializes a new user account with the provided userId, username, email, and passwordHash.
 * - getUserId - Getter method to retrieve the unique identifier for the user account
 * - getUsername - Getter method to retrieve the username associated with the user account
 * - getEmail - Getter method to retrieve the email address associated with the user account
 * - getPasswordHash - Getter method to retrieve the hashed password for the user account
 * - passwordMatch - This method takes in a plaintext password and compares it to the stored password
*/

public class UserAccount {
    
    private final String userId; //unique numerical ID number for every account
    private final String username;
    private final String email;
    private final String passwordHash;

    /**
     * UserAccount - Constructor for the UserAccount class, initializes a new user account with the provided userId, username, email, and passwordHash.
     * @param userId - A unique identifier for the user account
     * @param username - The username associated with the user account
     * @param email - The email address associated with the user account
     * @param passwordHash - The hashed password for the user account, used for authentication
     * @return - A new instance of the UserAccount class with the provided details
    */

    public UserAccount(String userId, String username, String email, String passwordHash) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * getUserId - Getter method to retrieve the unique identifier for the user account
     * @return - The userId of the user account
    */

    public String getUserId() {
        return userId;
    }

    /**
     * getUsername - Getter method to retrieve the username associated with the user account
     * @return - The username of the user account
    */
    public String getUsername() {
        return username;

    }

    /**
     * getEmail - Getter method to retrieve the email address associated with the user account
     * @return - The email of the user account
    */
    public String getEmail() {
        return email;
    }

    /**
     * getPasswordHash - Getter method to retrieve the hashed password for the user account
     * @return - The passwordHash of the user account
    */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * passwordMatch - This method takes in a plaintext password and compares it to the stored password hash using BCrypt.checkpw. It returns true if the passwords match and false otherwise. This method is used to verify user credentials during login.
     * @param inputPassword - The plaintext password to compare against the stored password hash
     * @return - true if the input password matches the stored password hash, false otherwise
     */
    public boolean passwordMatch(String inputPassword) {
        return inputPassword != null && passwordHash != null && BCrypt.checkpw(inputPassword, passwordHash);
    }
    
}