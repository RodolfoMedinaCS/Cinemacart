package com.cinemacart;

/* Represents a booking made by a customer for a specific movie and showtime.
 * Contains details such as the movie, showtime, number of tickets, and total price.
 * Bookings are tied to emails rather than userId
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