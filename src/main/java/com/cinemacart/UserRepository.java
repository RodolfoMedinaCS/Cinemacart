package com.cinemacart;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.QueryDocumentSnapshot;



public class UserRepository {

    private final Firestore db;

    public UserRepository() {
        this.db = FirestoreClient.getFirestore();
    }

//Save user to Firestore database
public void save(UserAccount account) {
    try{
      Map <String, Object> fireData = new HashMap<>();
      fireData.put("userId", account.getUserId());  
      fireData.put("username", account.getUsername());
      fireData.put("email", account.getEmail());
      fireData.put("passwordHash", account.getPasswordH());

      db.collection("users").document(account.getUsername()).set(fireData).get();

    } catch (Exception e) { 
        throw new RuntimeException("Run time error occurred", e);
    }
}


public boolean exists(String username) {
    try {
        DocumentSnapshot snapshot = db.collection("users").document(username).get().get(); //Get the document snapshot for the specified username
        return snapshot.exists(); //Return true if username/document in database exists, otherwise return false

    } catch (Exception e) {
        throw new RuntimeException("Error retrieving user", e);
    }
}

public UserAccount findByUsername(String username) {
    try {
        DocumentSnapshot snapshot = db.collection("users").document(username).get().get();

        if(!snapshot.exists()){
            return null; //Return null if user with specified username does not exist in database
    }
}