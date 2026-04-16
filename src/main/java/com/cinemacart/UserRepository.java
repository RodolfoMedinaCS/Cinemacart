package com.cinemacart;
import java.util.HashMap;
import java.util.Map;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

/**
 * a. UserRepository
 * b. Date created: 
 * c. Author: Winter Tomas
 * 
 * d. The primary method of this class is to save user accounts to a database upon creation. Other methods include checking existence of the account within the database, searching and
 * returning a user along with their data, and an account deletion method.
 * Firestore utilizes a fieldname and value to store in the database, in the save method, we are inputting a string for the fieldname and the user's data onto a hashmap which 
 * is then imported to the Firestore database.    
 * 
 * e. Methods:
 * 
 * UserRepository - Constructor for the UserRepository class, initializes the Firestore database connection that will be used for saving and retrieving user accounts in the database
 * @return - A new instance of the UserRepository class with an initialized Firestore database connection
 * 
 * save - This method takes in a UserAccount object and saves its data to the "users" collection in the Firestore database, using the email as the document ID.
 * It creates a map of the user data and uses the Firestore API to save it to the database.
 * @param account - The UserAccount object that contains the data to be saved to the database
 * 
 * exists - This method takes in an email and checks if a user account with that email exists in the "users" collection in the Firestore database. It returns true if the account exists and false otherwise.
 * @param email - The email address to check for existence in the database
 * @return - true if a user account with the specified email exists in the database, false
 * 
 * findByEmail - This method takes in an email and retrieves the corresponding user document from the "users" collection in the Firestore database. It returns a UserAccount object with the retrieved data if the account exists, or null if it does not.
 * @param email - The email address to search for in the database
 * @return - A UserAccount object with the retrieved data if the account exists, or null
 * 
 * deleteUser - This method takes in an email and deletes the corresponding user document from the "users" collection in the Firestore database. This method can be called to give users the option to delete their account.
 * @param email - The email address of the user account to be deleted
 * 
 * f. Data structures:
 * A map is used to store the user data when saving a user account to the database in the save method. 
 * The map allows us to associate field names (such as "email", "username", etc.) with their corresponding values from the UserAccount object, which can then be easily saved to the Firestore database.
 * @param String - The field name used in the Firestore database to identify the type of data being stored (e.g., "email", "username", etc.)
 * @param Object - The value associated with the field name, which is the actual data being stored in the database (e.g., the user's email address, username, etc.)
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
