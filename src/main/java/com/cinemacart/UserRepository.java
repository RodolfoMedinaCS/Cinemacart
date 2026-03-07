package com.cinemacart;
import java.util.HashMap;
import java.util.Map;
import javax.management.RuntimeErrorException;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.QueryDocumentSnapshot;

/* The primary method of this class is to save user accounts to a database upon creation. Other methods include checking existence of the account within the database, searching and
 * returning a user along with their data, and an account deletion method.
 * 
 * Firestore utilizes a fieldname and value to store in the database, in the save method, we are inputting a string for the fieldname and the user's data onto a hashmap which 
 * is then imported to the Firestore database.    
*/

public class UserRepository {

    private final Firestore db; 

    public UserRepository() {
        this.db = FirestoreClient.getFirestore();
    }

// Save user to Firestore database
public void save(UserAccount account) { // Account is the user object being saved to the database in this method
    try {
      Map <String, Object> fireData = new HashMap<>();
      fireData.put("userId", account.getUserId());  
      fireData.put("username", account.getUsername());
      fireData.put("email", account.getEmail());
      fireData.put("password", account.getPasswordHash());

      db.collection("users").document(account.getEmail()).set(fireData).get(); // Saves the user data to the "users" collection in Firestore database, using the username as the document ID

    } catch (Exception e) { 
        throw new RuntimeException("Run time error occurred", e);
    }
}

public boolean exists(String email) {
    try {
        DocumentSnapshot snapshot = db.collection("users").document(email).get().get(); // Get the document snapshot for the specified username
        return snapshot.exists(); // Return true if username/document in database exists, otherwise return false

    } catch (Exception e) {
        throw new RuntimeException("Error retrieving user", e);
    }
}

public UserAccount findByEmail(String email) {
    try {
        DocumentSnapshot snapshot = db.collection("users").document(email).get().get(); // Read document from "users" collection on Firestore

        if(!snapshot.exists()) {
            return null; // Return null if user with specified username does not exist in database
    }

        String userId = snapshot.getString("userId");
        String username = snapshot.getString("username");
        String passwordHash = snapshot.getString("password"); // Password hash is retrieved from the database password field and stored in the passwordHash variable, this is used to compare with the input password when user tries to login

        return new UserAccount(userId, username, email, passwordHash); // Return a new user account object with the retrieved data from the database

} catch (Exception e) {
    throw new RuntimeException("Could not find user", e);
    }
}
 
public void deleteUser(String email) { // Delete user by username, call this method to give user an option to delete account
    try {
       db.collection("users").document(email).delete().get();
    } catch (Exception e) {
        throw new RuntimeException ("Failed to delete user", e);
    }
    }
}
