package system.movie_reservation.exception;

import system.movie_reservation.model.movie.Movie;

public class MovieValidationHandler {

    public static void checkFieldsEmpty(Movie movie){

        String response = "";

        if(movie.getName().isEmpty())
            response += "the field name is required.";
        if(movie.getDescription().isEmpty())
            response += "the field description is required.";
        if(movie.getDuration().isEmpty())
            response += "the field duration is required.";
        if(movie.getCategories().isEmpty())
            response += "the field categories is required.";
        if(movie.getPosterUrl().isEmpty())
            response += "the field posterUrl is required.";
        if(movie.getReleaseDate() == 0)
            response += "the field releaseDate is required.";

        if (!response.isEmpty())
            throw new IllegalArgumentException(response.replace(".", "\n"));
    }
}
