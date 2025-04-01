package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.exception.TicketValidationHandler;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.MovieTheater;
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
    private final UserServiceImp userServiceImp;
    private final MovieServiceImp movieServiceImp;
    private final MovieTheaterService movieTheaterService;
    private final SeatTicketService seatTicketService;

    public TicketServiceImp(TicketRepository ticketRepository,
                            UserServiceImp userServiceImp,
                            MovieServiceImp movieServiceImp,
                            MovieTheaterService movieTheaterService,
                            SeatTicketService seatTicketService) {
        this.ticketRepository = ticketRepository;
        this.userServiceImp = userServiceImp;
        this.movieServiceImp = movieServiceImp;
        this.movieTheaterService = movieTheaterService;
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
        User user = userServiceImp.getUserById(ticketRequest.userId());
        Movie movie = movieServiceImp.getMovieById(ticketRequest.movieId());
        MovieTheater movieTheater = movieTheaterService.getSeatByMovieAndMovieTime(
                movie,
                ticketRequest.movieTime().toMovieTime()
        );
        Ticket ticket = new Ticket(user, movie, ticketRequest);
        ticket.setMovieTheater(movieTheater);

        seatTicketService.saveSeatWithTickets(ticket);
        ticketRepository.save(ticket);

        return new TicketResponse(ticket);
    }

    @Override
    public List<TicketResponse> getAllTickets() {

        return ticketRepository.findAll().stream()
                .map(TicketResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public TicketResponse updateTicket(TicketRequestUpdate ticketReqUpdate) {

        TicketValidationHandler.updateMethodEmptyFieldVerifier(ticketReqUpdate);
        Movie movie = movieServiceImp.getMovieById(ticketReqUpdate.movieId());
        MovieTheater movieTheater = movieTheaterService.getSeatByMovieAndMovieTime(
                movie,
                ticketReqUpdate.movieTime().toMovieTime()
        );
        Ticket oldTicket = getTicketById(ticketReqUpdate.id());

        Ticket newTicket = new Ticket(
                oldTicket.getUser(),
                movie,
                movieTheater,
                ticketReqUpdate
        );

        seatTicketService.updateTicketFromSeat(oldTicket, newTicket);
        ticketRepository.save(newTicket);

        return new TicketResponse(newTicket);
    }

    @Override
    public String deleteTicketById(Long id) {
        Ticket ticketById = getTicketById(id);
        seatTicketService.removeTicketFromSeat(ticketById);
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