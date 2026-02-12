package com.cinemacart;
//This class stores movie information like id,title, genre, and duration.
public class Movie {
    private final int movieId;//in case if two movies have same title
    private final String title;//movie title or name
    private final String genre;//title like action, drama, and ...
    private final int duration;//duration in minutes

    //takes the movie information and store it in this movie object
    public Movie(int movieId, String title, String genre, int duration) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
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
}
