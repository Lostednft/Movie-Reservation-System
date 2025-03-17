package system.movie_reservation.exception;

import system.movie_reservation.model.dto.MovieRequest;

public class ValidateException {

    public static void checkFieldsEmpty(MovieRequest movieRequest){

        String response = "";

        if(movieRequest.name().isEmpty())
            response = "the field name is required.";
        if(movieRequest.description().isEmpty())
            response += "the field description is required.";
        if(movieRequest.duration().isEmpty())
            response += "the field duration is required.";
        if(movieRequest.categories().isEmpty())
            response += "the field categories is required.";
        if(movieRequest.posterUrl().isEmpty())
            response += "the field posterUrl is required.";
        if(movieRequest.releaseDate() == 0)
            response += "the field releaseDate is required.";

        if (!response.isEmpty())
            throw new IllegalArgumentException(response.replace(".", "\n"));

    }
}
