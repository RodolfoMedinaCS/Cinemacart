package com.cinemacart;

import java.util.*;

public class Cart {

    // Store selected seats and avoid duplicates,also keeps the order
    private final Set<String> selectedSeats =  new LinkedHashSet<>();

    //The  date and time the user chose
    private String selectedDate;
    private String selectedTime;

    //the movie's ID and title
    private Integer movieId;
    private String movieTitle;

    // The ticket counts
    private int adultCount;
    private int childCount;
    private int seniorCount;

    //get all the seats which picked by the user
    public List<String> getSelectedSeats() {
         return new ArrayList<>(selectedSeats);
    }

    //  Set the seat list and removing any that were there before
    public void setSelectedSeats(List<String> seats) {
        selectedSeats.clear();
        if (seats != null) {
            for (String seat : seats) {
                addSeat(seat);
            }

        }
    }

    //Add one seat if the seat code is valid
    public void addSeat(String seat) {
        if (seat != null) {
            seat = seat.toUpperCase(); // normalize input
        }

        if (!isValidSeat(seat)) {
            throw new IllegalArgumentException("Seat must be between A1 and F8");
        }

        selectedSeats.add(seat);
    }

    //this method remove a seat from the cart
    public void removeSeat(String seat) {
        if (seat != null) {
            seat = seat.toUpperCase();
        }
        selectedSeats.remove(seat);
    }

    //This method return how many seats are in the cart
    public int getSeatCount() {
        return selectedSeats.size();
    }

    //get the date chosed by the user
    public String getSelectedDate() {
        return selectedDate;
    }

    //Set the date and make sure it is not empty
    public void setSelectedDate(String selectedDate) {
        if (selectedDate == null || selectedDate.isBlank()) {
            throw new IllegalArgumentException("Date cannot be empty");
        }

        this.selectedDate = selectedDate;
    }

    //get the time selected by the user
    public String getSelectedTime() {
        return selectedTime;
    }

    //This method set the time and make sure it must not be empty
    public void setSelectedTime(String time) {
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("Time cannot be empty");
        }
        this.selectedTime = time;
    }

    //  Get the movie ID
    public Integer getMovieId() {
        return movieId;
    }



    // Set the movie ID and it cannot be null
    public void setMovieId(Integer movieId) {
        if (movieId == null) {
            throw new IllegalArgumentException("Movie ID cannot be null");
        }
        this.movieId = movieId;
    }

    // get movie tile
    public String getMovieTitle() {
        return movieTitle;
    }

    // Set the movie title and also make sure it must not be empty
    public void setMovieTitle(String movieTitle) {
        if (movieTitle == null || movieTitle.isBlank()) {
            throw new IllegalArgumentException("Movie title cannot be empty");
        }
        this.movieTitle = movieTitle;
    }

    //The ticket count getters
    public int getAdultCount() {
        return adultCount;
    }


    public int getChildCount() {
        return childCount;
    }

    public int getSeniorCount() {
        return seniorCount;
    }

    //ticket count setters and negative values are not allowed here
    public void setAdultCount(int count) {
        if (count < 0) throw new IllegalArgumentException("Invalid adult count");
        this.adultCount = count;
    }


    public void setChildCount(int count) {
        if (count < 0) throw new IllegalArgumentException("Invalid child count");
        this.childCount = count;
    }

    public void setSeniorCount(int count) {
        if (count < 0) throw new IllegalArgumentException("Invalid senior count");
        this.seniorCount = count;
    }

    // all the total tickets selected
    public int getTotalTickets() {
        return adultCount + childCount + seniorCount;
    }

    //This method validate that ticket count and matches seat count
    public boolean isTicketSeatMatch() {
        return getTotalTickets() == getSeatCount();
    }

    //get the subtotal ... the values come from the frontend
    public double getSubtotal() {
        return (adultCount * 14.99)
                + (childCount * 9.99)
                + (seniorCount * 11.99);
    }

    //Check if the seat code is valid. For example  A1 or F8 are valid
    private boolean isValidSeat(String seat) {
        return seat != null && seat.matches("^[A-F][1-8]$");
    }

    //this method remove everything from the cart
    public void clear() {
        selectedSeats.clear();
        selectedDate = null;
        selectedTime = null;
        movieId = null;
        movieTitle = null;
        adultCount = 0;
        childCount = 0;
        seniorCount = 0;
    }

    //This method check if there are no seats in the cart
    public boolean isEmpty() {
        return selectedSeats.isEmpty();
    }


}