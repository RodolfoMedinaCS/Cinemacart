//Creates the actual User objects

package com.cinemacart;

import java.util.UUID; //Unique User Identifier, unique number assigned to every user

public class AccountCreation {

private UserRepository repo;

public AccountCreation(UserRepository repo){
   this.repo = repo;
}

    public UserAccount createAccount(String username, String email, String password){
       
        String userId = UUID.randomUUID().toString(); //Unique User Identifier tied to User string
        String passwordHash = hash(password); //Password hash
        //Input validation
        if (username == null || username.isBlank()){
            throw new IllegalArgumentException ("Username cannot be blank");
        }
        if (email == null || email.isBlank()){
            throw new IllegalArgumentException ("Email cannot be blank");
        }
        if (password == null || password.isBlank()){
            throw new IllegalArgumentException ("Password cannot be blank");
        }
        if (repo.exists(username)){ //Checks UserRepository if username string is used
            throw new IllegalArgumentException("Username is already taken");
        }
   
        UserAccount account = new UserAccount(userId, username, email, passwordHash); //User object is created
       
        repo.save(account); //Store the user object in repository
       
        return account;
    }
   
    private String hash(String password){
        return password;
    }
}