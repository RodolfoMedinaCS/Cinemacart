package com.cinemacart;

import java.util.List;

// This class stores all the available movies
public class MovieRepository {

    // Return the list of available movies
    public static List<Movie> getAllMovies() {

        return List.of(
                new Movie(1, "Titanic", "Drama", 195, 8.0),
                new Movie(2, "The Godfather I", "Crime", 175, 9.2),
                new Movie(3, "The Godfather II", "Crime", 202, 9.0),
                new Movie(4, "Gladiator", "Action", 155, 8.5),
                new Movie(5, " 3 Idiots", "Comedy", 171, 8.4),
                new Movie(6, "The Batman", "Action ", 176, 7.8),
                new Movie(7, "The Lion King", "Animation", 88, 8.5),
                new Movie(8, "Fury", "War", 134, 7.6),
                new Movie(9, "The Kite Runner", "Drama", 128, 7.6),
                new Movie(10, "Avatar: Fire and Ash ", " Adventure", 197, 7.4),
                new Movie(11, "The Dark Knight", "Action", 152, 9.0),
                new Movie(12, "Beauty and the Beast ", "Romance", 129, 7.1)
        );


    }
}
