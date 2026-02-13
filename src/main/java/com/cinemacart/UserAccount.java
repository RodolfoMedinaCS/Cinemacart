//Holds the users information, user ID, username, and email

import java.security.SecureRandom;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

package com.cinemacart;

public class UserAccount {
    
    private final String userId; //unique numerical ID number for every account
    private final String username;
    private final String email;
    private final String passwordH;

    public UserAccount(String userId, String username, String email, String password) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;

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

    //Checks if password hash matches stored hash
    public boolean passwordMatch(String hash) {

            return passwordH.equals(hash);
            }
}


public String getPasswordH() {
    return passwordH;
}
/* 2/13/26 
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



public String getPassword() {

    }