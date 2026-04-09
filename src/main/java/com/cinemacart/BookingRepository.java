package com.cinemacart;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.DocumentSnapshot;

/* The primary method of this class is to save user bookings to the Firebase database upon creation. Other methods include checking booking history of an account within the database
 * Similarly to the UserRepository class, objects are stored in the Firebase database. They are specifically stored in a collection called Bookings which serves as a "folder" for all booking documents.
 * Each booking document can be considered as a file within the Booking collection / folder. Each booking object in this case is a file that contains the booking data that is saved to a user's account.
*/

public class BookingRepository { 

    private final Firestore db;

    public BookingRepository() {
        this.db = FirestoreClient.getFirestore(); // Initialize the Firestore database connection in the constructor of the BookingRepository class, allowing it to be used for saving and deleting bookings in the database
    }

    // Method to save a booking to the Firestore database, takes in a Booking object and saves its data to the "bookings" collection in the database using the bookingId as the document ID
    public void save(Booking booking) {
        try {
            Map <String, Object> bookingData = new HashMap<>();
            bookingData.put("bookingId", booking.getBookingId());
            bookingData.put("email", booking.getEmail());
            bookingData.put("movieId", booking.getMovieId());
            bookingData.put("bookingDate", booking.getBookingDate());
            bookingData.put("status", booking.getStatus());
            bookingData.put("movieTitle", booking.getMovieTitle());
            bookingData.put("purchaseDate", booking.getPurchaseDate());
            db.collection("bookings").document(booking.getBookingId()).set(bookingData).get(); // Saves the booking data to the "bookings" collection in Firestore database, using the bookingId as the document ID
        } catch (Exception e) {
            throw new RuntimeException("Error saving booking", e);
        }
    }

    public void cancelBooking(String bookingId) {
        try {
            db.collection("bookings").document(bookingId).update("status", "cancelled").get(); // Updates the "status" field of the booking document with the specified bookingId to "cancelled" in the "bookings" collection in Firestore database
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling booking, try again", e);
        }
    }

    // Only accessible if booking has been cancelled, deletes the booking document from the "bookings" collection in Firestore database using the specified bookingId
    public void deleteBooking(String bookingId) {
        try {
            
            DocumentSnapshot snapshot = db.collection("bookings").document(bookingId).get().get();
            if (!snapshot.exists()) {
                throw new RuntimeException("Booking not found");
            }
            String status = snapshot.getString("status");
            if (!"cancelled".equalsIgnoreCase(status)) {
                throw new RuntimeException("Only cancelled bookings can be deleted");
            }
            db.collection("bookings").document(bookingId).delete().get(); // Deletes the booking document with the specified bookingId from the "bookings" collection in Firestore database
        } catch (Exception e) {
            throw new RuntimeException("Error deleting booking, try again", e);
        }
    }



    // Method to find bookings by email and return a list of Booking objects associated with that email that is all retrieved from the Firestore database, Firebase
    public List<Booking> findByEmail(String email) {
        try {
            List<Booking> bookings = new ArrayList<>();
            List<QueryDocumentSnapshot> documents = db.collection("bookings").whereEqualTo("email", email).get().get().getDocuments(); // Query the "bookings" collection in Firestore database to find all documents where the "email" field matches the specified email, and retrieve the matching documents as a list of QueryDocumentSnapshot objects

            for (QueryDocumentSnapshot doc : documents) {
                String bookingId = doc.getString("bookingId");
                String movieId = doc.getString("movieId");
                String bookingDate = doc.getString("bookingDate");
                String status = doc.getString("status");
                String movieTitle = doc.getString("movieTitle");
                String purchaseDate = doc.getString("purchaseDate");
                Booking booking = new Booking(email, bookingId, movieId, bookingDate, status, movieTitle, purchaseDate); // Create a new Booking object using the retrieved data from the document
                bookings.add(booking); // Add the Booking object to the list of bookings
            }
            return bookings; // Return the list of bookings associated with the specified email
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving bookings", e);
        }
    }
}