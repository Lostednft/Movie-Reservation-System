package system.movie_reservation.exception;

import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.ticket.TicketRequestUpdate;

public class TicketValidationHandler {

    public static void createMethodEmptyFieldVerifier(TicketRequest ticketRequest){

        String response = "";

        if(ticketRequest.userId().isEmpty())
            response += "the field userId is required.";
        if(ticketRequest.movieId().isEmpty())
            response += "the field movieId is required.";
        if(ticketRequest.movieTime() == null)
            response += "the field movieTime is required.";
        if(ticketRequest.seat().isEmpty())
            response += "the field seat is required.";

        if (!response.isEmpty())
            throw new IllegalArgumentException(response.replace(".", "\n"));
    }

    public static void updateMethodEmptyFieldVerifier(TicketRequestUpdate ticketRequest){

        String response = "";

        if(ticketRequest.id() == 0)
            response += "the field ID is required.";
        if(ticketRequest.movieId().isEmpty())
            response += "the field movieId is required.";
        if(ticketRequest.movieTime() == null)
            response += "the field movieTime is required.";
        if(ticketRequest.seat().isEmpty())
            response += "the field seat is required.";

        if (!response.isEmpty())
            throw new IllegalArgumentException(response.replace(".", "\n"));
    }
}
