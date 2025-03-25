package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.model.Movie.Movie;
import system.movie_reservation.model.Seat.Seat;
import system.movie_reservation.model.Ticket.Ticket;
import system.movie_reservation.model.User.User;
import system.movie_reservation.model.Ticket.TicketRequest;
import system.movie_reservation.model.Ticket.TicketRequestUpdate;
import system.movie_reservation.model.Ticket.TicketResponse;
import system.movie_reservation.repository.TicketRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final MovieService movieService;
    private final SeatQueryService seatQueryService;
    private final SeatTicketService seatTicketService;

    public TicketService(TicketRepository ticketRepository,
                         UserService userService,
                         MovieService movieService,
                         SeatQueryService seatQueryService, SeatTicketService seatTicketService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.movieService = movieService;
        this.seatQueryService = seatQueryService;
        this.seatTicketService = seatTicketService;
    }


    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No ticket found with this ID."));
    }

    @Transactional
    public TicketResponse createTicket(TicketRequest ticketRequest) {

        User user = userService.getUserById(ticketRequest.userId());
        Movie movie = movieService.getMovieById(ticketRequest.movieId());
        Seat seat = seatQueryService.getSeatByMovieAndMovieTime(
                movie,
                ticketRequest.movieTime().toMovieTime()
        );
        Ticket ticket = new Ticket(user, movie, ticketRequest);
        ticket.setRoomSeats(seat);

        seatTicketService.saveSeatWithTickets(ticket);
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
        Seat seat = seatQueryService.getSeatByMovieAndMovieTime(
                movie,
                ticketReqUpdate.movieTime().toMovieTime()
        );

        Ticket ticketById = getTicketById(ticketReqUpdate.id());
        ticketById.setMovie(movie);
        ticketById.setRoomSeats(seat);
        ticketById.setSeat(ticketReqUpdate.seat());

        seatTicketService.updateAndRemoveTicketFromSeat(ticketById, "update");
        return new TicketResponse(ticketById);
    }

    public String deleteTicketById(Long id) {
        Ticket ticketById = getTicketById(id);
        seatTicketService.updateAndRemoveTicketFromSeat(ticketById, "delete");
        ticketRepository.delete(ticketById);
        return "Ticket was deleted successfully";
    }

    public String deleteAllTickets() {
        seatTicketService.deleteAllTicketsFromSeat();
        ticketRepository.deleteAll();
        return "All Tickets was deleted";
    }
}
