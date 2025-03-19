package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.Ticket;
import system.movie_reservation.model.User;
import system.movie_reservation.model.dto.TicketDto;
import system.movie_reservation.repository.MovieRepository;
import system.movie_reservation.repository.SeatRepository;
import system.movie_reservation.repository.TicketRepository;
import system.movie_reservation.repository.UserRepository;

import java.util.Map;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private final SeatRepository seatRepository;


    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository,
                         MovieRepository movieRepository, SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.seatRepository = seatRepository;
    }

    public Ticket createTicket(TicketDto ticketDto){

        User user = userRepository.findById(ticketDto.userId()).get();
        Movie movie = movieRepository.findById(ticketDto.movieId()).get();
        Ticket ticket = new Ticket(user, movie, ticketDto);
        return ticketRepository.save(ticket);
    }


}
