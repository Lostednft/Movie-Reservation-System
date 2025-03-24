package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.Seat;
import system.movie_reservation.model.Ticket;
import system.movie_reservation.model.User;
import system.movie_reservation.model.request.TicketRequest;
import system.movie_reservation.model.request.ToUpdate.TicketRequestUpdate;
import system.movie_reservation.model.response.TicketResponse;
import system.movie_reservation.repository.TicketRepository;

import java.util.List;
import java.util.NoSuchElementException;

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


    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No ticket found with this ID."));
    }

    @Transactional
    public TicketResponse createTicket(TicketRequest ticketRequest) {

        User user = userService.getUserById(ticketRequest.userId());
        Movie movie = movieService.getMovieById(ticketRequest.movieId());
        Seat seat = seatService.getSeatByMovieAndMovieTime(
                movie,
                ticketRequest.movieTime().toMovieTime()
        );
        Ticket ticket = new Ticket(user, movie, ticketRequest);
        ticket.setRoomSeats(seat);

        seatService.saveSeatWithTickets(ticket);
        ticketRepository.save(ticket);

        return new TicketResponse(ticket);
    }

    public List<TicketResponse> geAllTickets() {
        return ticketRepository.findAll().stream()
                .map(TicketResponse::new)
                .toList();
    }

    @Transactional
    public TicketResponse updateTicket(TicketRequestUpdate ticketReqUpdate) {

        Movie movie = movieService.getMovieById(ticketReqUpdate.movieId());
        Seat seat = seatService.getSeatByMovieAndMovieTime(
                movie,
                ticketReqUpdate.movieTime().toMovieTime()
        );

        Ticket ticketById = getTicketById(ticketReqUpdate.id());
        ticketById.setMovie(movie);
        ticketById.setRoomSeats(seat);
        ticketById.setSeat(ticketReqUpdate.seat());

        seatService.updateSeatWithTicketUpdated(ticketById);
        return new TicketResponse(ticketById);
    }
}
