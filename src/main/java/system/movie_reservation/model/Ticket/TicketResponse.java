package system.movie_reservation.model.Ticket;

import java.time.LocalTime;
import java.util.List;

public record TicketResponse(Long id,
                             String user_id,
                             String movie_id,
                             LocalTime movieTimeStart,
                             LocalTime movieTimeEnd,
                             List<String> seats,
                             Long room_id) {

    public TicketResponse(Ticket ticket){
        this(
                ticket.getId(),
                ticket.getUser().getId(),
                ticket.getMovie().getId(),
                ticket.getMovieTime().getStartTime(),
                ticket.getMovieTime().getEndTime(),
                ticket.getSeat(),
                ticket.getRoomSeats().getId()
        );
    }
}
