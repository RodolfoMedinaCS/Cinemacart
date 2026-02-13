package com.cinemacart;

import java.util.HashMap;
import java.util.Map;
import javax.management.RuntimeErrorException;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.QueryDocumentSnapshot;

/* Firebase utilizes a fieldname  */

public class UserRepository {

    private final Firestore db;

    public UserRepository() {
        this.db = FirestoreClient.getFirestore();
    }

//Save user to Firestore database
public void save(UserAccount account) { //account is the user object being saved to the database in this method
    try {
      Map <String, Object> fireData = new HashMap<>();
      fireData.put("userId", account.getUserId());  
      fireData.put("username", account.getUsername());
      fireData.put("email", account.getEmail());
      fireData.put("password", account.getPassword());

      db.collection("users").document(account.getUsername()).set(fireData).get(); //saves the user data to the "users" collection in Firestore database, using the username as the document ID

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

        if(!snapshot.exists()) {
            return null; //Return null if user with specified username does not exist in database
    }
} catch (Exception e) {
    throw new RuntimeException("Could not find user", e);
}

}}