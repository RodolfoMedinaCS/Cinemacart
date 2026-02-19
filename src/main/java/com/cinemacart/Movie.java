package com.cinemacart;

import java.util.Objects;//I used it for requireNoNull for validation

//This class stores movie information like id,title, genre, and duration.
public class Movie {
    private final int movieId; //in case if two movies have same title
    private final String title; //movie title or name
    private final String genre; //title like action, drama, and ...
    private final int duration; //duration in minutes
    private final double rating;

    //takes the movie information and store it in this movie object
    public Movie(int movieId, String title, String genre, int duration, double rating) {

        //make sure title and genre are not null
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(genre, "genre must not be null");

        //make sure duration is positive
        if (duration <= 0) {
            throw new IllegalArgumentException("duration must be positive");
        }

        //make sure rating is between 0 and 10
        if (rating < 0.0 || rating > 10.0) {
            throw new IllegalArgumentException("rating must be between 0 and 10");
        }

        this.movieId = movieId;
        this.title = title.trim(); //remove extra spaces
        this.genre = genre.trim(); //remove extra spaces
        this.duration = duration;
        this.rating = rating;
    }

    //return the movie id
    public int getMovieId() {
        return movieId;
    }

    //return the movie title
    public String getTitle() {
        return title;
    }

    //return the movie genre
    public String getGenre() {
        return genre;
    }

    //return the movie duration in minutes
    public int getDuration() {
        return duration;
    }

    //return the movie rating
    public double getRating(){
        return rating;
    }
}
