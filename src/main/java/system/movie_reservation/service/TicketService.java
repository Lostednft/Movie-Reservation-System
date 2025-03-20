package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.Seat;
import system.movie_reservation.model.Ticket;
import system.movie_reservation.model.User;
import system.movie_reservation.model.dto.TicketRequest;
import system.movie_reservation.model.dto.TicketResponse;
import system.movie_reservation.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final MovieService movieService;
    private final SeatService seatService;

    public TicketService(TicketRepository ticketRepository,
                         UserService userService,
                         MovieService movieService,
                         SeatService seatService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.movieService = movieService;
        this.seatService = seatService;
    }

    @Transactional
    public TicketResponse createTicket(TicketRequest ticketRequest) {

        User user = userService.getUserById(ticketRequest.userId());
        Movie movie = movieService.getMovieById(ticketRequest.movieId());
        Seat seat = seatService.getSeatByMovieAndMovieTime(
                movie,
                ticketRequest.movieTime().toMovieTime());
        Ticket ticket = new Ticket(user, movie, ticketRequest);

        ticket.setRoomSeats(seat);
        ticketRepository.save(ticket);

        return new TicketResponse(ticket);
    }
}
