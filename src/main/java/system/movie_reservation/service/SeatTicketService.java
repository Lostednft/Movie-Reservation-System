package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.model.seat.SeatTicket;
import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.repository.MovieTheaterRepository;

import java.util.List;

@Service
public class SeatTicketService {

    private final MovieTheaterRepository movieTheaterRepository;

    public SeatTicketService(MovieTheaterRepository movieTheaterRepository) {
        this.movieTheaterRepository = movieTheaterRepository;
    }

    void saveSeatWithTickets(Ticket ticket){

        MovieTheater roomSeats = ticket.getMovieTheater();
        List<SeatTicket> seatTickets = roomSeats.getSeatTicket();

        for (String seat : ticket.getSeat()) {

            seatTickets.stream().filter(n -> n.getSeatKey().equals(seat))
                    .findFirst()
                    .ifPresent(st -> st.setTicketValue(ticket));
        }
        roomSeats.setSeatTicket(seatTickets);
        movieTheaterRepository.save(roomSeats);
    }

    void updateAndRemoveTicketFromSeat(Ticket oldTicket,
                                       Ticket newTicket,
                                       String methodHttp){

        MovieTheater roomSeats = oldTicket.getMovieTheater();
        List<SeatTicket> seatTickets = roomSeats.getSeatTicket();

        seatTickets.stream()
                .filter(st -> st.getTicketValue() != null &&
                        st.getTicketValue().equals(oldTicket))
                .forEach(st -> st.setTicketValue(null));

        roomSeats.setSeatTicket(seatTickets);
        movieTheaterRepository.save(roomSeats);

        if (methodHttp.equals("update"))
            saveSeatWithTickets(newTicket);
    }

    void deleteAllTicketsFromSeat(){
        List<MovieTheater> allMovieTheater = movieTheaterRepository.findAll();

        allMovieTheater.forEach(stTicket ->
                stTicket.getSeatTicket().forEach(value ->
                        value.setTicketValue(null)
                )
        );
        movieTheaterRepository.saveAll(allMovieTheater);
    }
}