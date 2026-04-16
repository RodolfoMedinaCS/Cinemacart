package com.cinemacart;

/**
 * a. Booking
 * b. Date created: 
 * c. Author: Winter Tomas
 * 
 * d. Represents a booking made by a customer for a specific movie and showtime.
 * Contains details such as the movie, showtime, number of tickets, and total price.
 * Bookings are tied to emails rather than userId
 * 
 * e. Methods:
 * 
 * Booking - Constructor to initialize a booking with the provided details
 * @param email - The email of the customer who made the booking
 * @param bookingId - A unique identifier for the booking
 * @param movieId - The identifier for the movie that was booked
 * @param bookingDate - The date and time of the showtime for which the booking was
 * @param status - The current status of the booking (e.g., "active", "cancelled")
 * @param movieTitle - The title of the movie that was booked
 * @param purchaseDate - The date and time when the booking was made
 * @return - A new instance of the Booking class with the provided details
 * 
 * getEmail - Getter method to retrieve the email associated with the booking
 * @return - The email of the customer who made the booking
 * 
 * getBookingId - Getter method to retrieve the unique identifier for the booking
 * @return - The bookingId of the booking
 * 
 * getMovieId - Getter method to retrieve the identifier for the movie that was booked
 * @return - The movieId of the booking
 * 
 * getBookingDate - Getter method to retrieve the date and time of the showtime for which the booking was made
 * @return - The bookingDate of the booking
 * 
 * getStatus - Getter method to retrieve the current status of the booking
 * @return - The status of the booking (e.g., "active", "cancelled
 * 
 * getMovieTitle - Getter method to retrieve the title of the movie that was booked
 * @return - The movieTitle of the booking
 * 
 * getPurchaseDate - Getter method to retrieve the date and time when the booking was made
 * @return - The purchaseDate of the booking
 * 
 * setStatus - Setter method to update the current status of the booking
 * @param status - The new status to set for the booking (e.g., "active
*/     

public class Booking {

    private final String email;
    private final String bookingId;
    private final String movieId;
    private final String bookingDate;
    private String status;
    private final String movieTitle;
    private final String purchaseDate;

    // Constructor to initialize a booking with the provided details
    public Booking (String email, String bookingId, String movieId, String bookingDate, String status, String movieTitle, String purchaseDate) {
        this.email = email;
        this.bookingId = bookingId;
        this.movieId = movieId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.movieTitle = movieTitle;
        this.purchaseDate = purchaseDate;
    }

    // Getters for the booking details
    public String getEmail() {
        return email;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}