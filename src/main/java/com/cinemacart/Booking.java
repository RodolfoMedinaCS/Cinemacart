package com.cinemacart;

/**
 * Booking
 * Date created: Q1 of 2026
 * Author: Winter Tomas
 * 
 * Represents a booking made by a customer for a specific movie and showtime.
 * Contains details such as the movie, showtime, number of tickets, and total price.
 * Bookings are tied to emails rather than userId
 * 
 * Methods:
 * Booking - Constructor to initialize a booking with the provided details
 * getEmail - Getter method to retrieve the email associated with the booking
 * getBookingId - Getter method to retrieve the unique identifier for the booking
 * getMovieId - Getter method to retrieve the identifier for the movie that was booked
 * getBookingDate - Getter method to retrieve the date and time of the showtime for which
 * getStatus - Getter method to retrieve the current status of the booking
 * getMovieTitle - Getter method to retrieve the title of the movie that was booked
 * getPurchaseDate - Getter method to retrieve the date and time when the booking was made
 * setStatus - Setter method to update the current status of the booking
*/     

public class Booking {

    private final String email;
    private final String bookingId;
    private final String movieId;
    private final String bookingDate;
    private String status;
    private final String movieTitle;
    private final String purchaseDate;
    private final double amount;

    
    /** 
     * Constructor to initialize a booking with the provided details
     * @param email - The email of the customer who made the booking
     * @param bookingId - A unique identifier for the booking
     * @param movieId - The identifier for the movie that was booked
     * @param bookingDate - The date and time of the showtime for which the booking was
     * @param status - The current status of the booking (e.g., "active", "cancelled")
     * @param movieTitle - The title of the movie that was booked
     * @param purchaseDate - The date and time when the booking was made
     **/
    public Booking (String email, String bookingId, String movieId, String bookingDate, String status, String movieTitle, String purchaseDate, double amount) {
        this.email = email;
        this.bookingId = bookingId;
        this.movieId = movieId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.movieTitle = movieTitle;
        this.purchaseDate = purchaseDate;
        this.amount = amount;
    }

    // Getters for the booking details

    /** 
    * getEmail - Getter method to retrieve the email associated with the booking
    * @return - The email of the customer who made the booking
    **/    
     public String getEmail() {
        return email;
    }

    /** 
    * getBookingId - Getter method to retrieve the unique identifier for the booking
    * @return - The bookingId of the booking
    **/   
    public String getBookingId() {
        return bookingId;
    }

    /**
    * getMovieId - Getter method to retrieve the identifier for the movie that was booked
    * @return - The movieId of the booking
    **/
    public String getMovieId() {
        return movieId;
    }

    /**
    * getBookingDate - Getter method to retrieve the date and time of the showtime for which the booking was made
    *@return - The bookingDate of the booking
    */
    public String getBookingDate() {
        return bookingDate;
    }

    /**
    * getStatus - Getter method to retrieve the current status of the booking
    * @return - The status of the booking: active or cancelled
    */
    public String getStatus() {
        return status;
    }

    /** 
    * getMovieTitle - Getter method to retrieve the title of the movie that was booked
    * @return - The movieTitle of the booking
    **/ 
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
    * getPurchaseDate - Getter method to retrieve the date and time when the booking was made
    * @return - The purchaseDate of the booking
    */
    public String getPurchaseDate() {
        return purchaseDate;
    }

    /**
    * setStatus - Setter method to update the current status of the booking
    * @param status - The new status to set for the booking (e.g., "active
    */
    public void setStatus(String status) {
        this.status = status;
    }

    public double amount() {
        return amount;
    }
}