package com.cinemacart;

import java.util.ArrayList;//used to store the filtered search results
import java.util.Comparator;//use to sort movies by rating or title
import java.util.List;//used to represent a list of Movie objects

// This class handle searching movies from the MovieRepository
public class Search {

    // this method search movies based on title, genre and minimum rating
    // Also, if the user wants it can sort the results by rating and title
    public static List<Movie> search(
            String titleQuery,
            String genreQuery,
            Double minRating,
            String sortBy) {

        // Clean the input by removing the spaces and converting to lower case
        String tq = normalize(titleQuery);
        String gq = normalize(genreQuery);

        // Get all movies from repository
        List<Movie> allMovies = MovieRepository.getAllMovies();

        // This list will store the matching movies
        List<Movie> results = new ArrayList<>();

        // Loop through every movie
        for (Movie movie : allMovies) {

            // we make sure that fields are not null
            String title = movie.getTitle() == null ? "" : movie.getTitle();
            String genre = movie.getGenre() == null ? "" : movie.getGenre();
            double rating = movie.getRating();

            // Check if movie partially matches the title for example: The Batman ... bat
            boolean matchesTitle = (tq == null) || title.toLowerCase().contains(tq);

            // Check if movie exactly matches genre
            boolean matchesGenre = (gq == null) || genre.toLowerCase().equals(gq);

            // Check if rating is greater than or equal to minimum rating
            boolean matchesRating = (minRating == null) || (rating >= minRating);

            // If all conditions are true then add movie to results
            if (matchesTitle && matchesGenre && matchesRating) {
                results.add(movie);
            }
        }

        // Sorting section
        if ("rating".equalsIgnoreCase(sortBy)) {
            // Sort by rating...highest rating comes first
            results.sort(Comparator.comparingDouble(Movie::getRating).reversed());

        } else if ("title".equalsIgnoreCase(sortBy)) {
            // Sort alphabetically by title
            results.sort(Comparator.comparing(
                    Movie::getTitle,
                    String.CASE_INSENSITIVE_ORDER
            ));
        }


        // Return final filtered list
        return results;
    }

    // This is a helper method to clean input strings
    private static String normalize(String s) {
        if (s == null) return null;
        s = s.trim();
            return s.isEmpty() ? null : s.toLowerCase();
    }
}
