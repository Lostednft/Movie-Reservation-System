package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.exception.TicketValidationHandler;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.Seat;
import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.model.user.User;
import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.ticket.TicketRequestUpdate;
import system.movie_reservation.model.ticket.TicketResponse;
import system.movie_reservation.repository.TicketRepository;
import system.movie_reservation.service.usescases.TicketUsesCases;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketServiceImp implements TicketUsesCases {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final MovieService movieService;
    private final SeatQueryService seatQueryService;
    private final SeatTicketService seatTicketService;

    public TicketServiceImp(TicketRepository ticketRepository,
                            UserService userService,
                            MovieService movieService,
                            SeatQueryService seatQueryService,
                            SeatTicketService seatTicketService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.movieService = movieService;
        this.seatQueryService = seatQueryService;
        this.seatTicketService = seatTicketService;
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No ticket found with this ID."));
    }

    @Transactional
    @Override
    public TicketResponse createTicket(TicketRequest ticketRequest) {

        TicketValidationHandler.createMethodEmptyFieldVerifier(ticketRequest);
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

    @Override
    public List<TicketResponse> geAllTickets() {

        return ticketRepository.findAll().stream()
                .map(TicketResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public TicketResponse updateTicket(TicketRequestUpdate ticketReqUpdate) {

        TicketValidationHandler.updateMethodEmptyFieldVerifier(ticketReqUpdate);
        Movie movie = movieService.getMovieById(ticketReqUpdate.movieId());
        Seat seat = seatQueryService.getSeatByMovieAndMovieTime(
                movie,
                ticketReqUpdate.movieTime().toMovieTime()
        );
        Ticket oldTicket = getTicketById(ticketReqUpdate.id());

        Ticket newTicket = new Ticket(oldTicket.getUser() ,movie, seat, ticketReqUpdate);

        seatTicketService.updateAndRemoveTicketFromSeat(
                oldTicket,
                newTicket,
                "update");
        ticketRepository.save(newTicket);

        return new TicketResponse(newTicket);
    }

    @Override
    public String deleteTicketById(Long id) {
        Ticket ticketById = getTicketById(id);
        seatTicketService.updateAndRemoveTicketFromSeat(
                ticketById,
                null,
                "delete");
        ticketRepository.delete(ticketById);
        return "Ticket was deleted successfully";
    }

    @Override
    public String deleteAllTickets() {
        if(ticketRepository.findAll().isEmpty())
            return "No tickets registered.";

        seatTicketService.deleteAllTicketsFromSeat();
        ticketRepository.deleteAll();
        return "All Tickets was deleted";
    }
}