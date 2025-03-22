package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.Enums.MovieTime;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.Seat;
import system.movie_reservation.model.SeatTicket;
import system.movie_reservation.model.Ticket;
import system.movie_reservation.repository.SeatRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat getSeatById(Long id){
        return seatRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No Seat found with this ID."));
    }

    public Seat getSeatByMovieAndMovieTime(Movie movie, MovieTime movieTime){
        return seatRepository.findByMovieAndMovieTime(movie, movieTime).orElseThrow(() ->
                new NoSuchElementException("No Seat found with this ID."));
    }

    public void saveSeatWithTicketsUpdated(Ticket ticket){

        Seat roomSeats = ticket.getRoomSeats();
        List<SeatTicket> seatTickets = roomSeats.getSeatTicket();

        for (String seat : ticket.getSeat()) {

            seatTickets.stream().filter(n -> n.getSeatKey().equals(seat))
                    .findFirst()
                    .ifPresent(st -> st.setTicketValue(ticket));
        }

        roomSeats.setSeatTicket(seatTickets);
        seatRepository.save(roomSeats);

    }
}
