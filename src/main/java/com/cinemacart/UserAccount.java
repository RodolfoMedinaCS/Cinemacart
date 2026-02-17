//Holds the users information, user ID, username, and email
package com.cinemacart;

import org.mindrot.jbcrypt.BCrypt;

public class UserAccount {
    
    private final String userId; //unique numerical ID number for every account
    private final String username;
    private final String email;
    private final String passwordHash;

    public UserAccount(String userId, String username, String email, String passwordHash) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;

    }

    public String getEmail() {
        return email;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean passwordMatch(String inputPassword) {
        return inputPassword != null && passwordHash != null && BCrypt.checkpw(inputPassword, passwordHash);
    }
    
}




/*
public String getPasswordH() {
    return passwordH;
}
 
public PasswordHash hashPassword(String password) {

SecureRandom random = new SecureRandom();
byte[] salt = new byte[16];
random.nextBytes(salt);

KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

byte[] hash = factory.generateSecret(spec).getEncoded();

PasswordHash passwordHash = new PasswordHash(hash, salt);


return passwordHash;
}
*/