package system.movie_reservation.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import system.movie_reservation.model.movie.EnumLoader.Category;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.ticket.TicketRequestUpdate;
import system.movie_reservation.model.ticket.TicketResponse;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.repository.TicketRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TicketServiceLayerTest {

    @InjectMocks
    private TicketServiceImp ticketServiceImp;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private MovieServiceImp movieServiceImp;

    @Mock
    private MovieTheaterService movieTheaterService;

    @Mock
    private SeatTicketService seatTicketService;

    @Mock
    private UserServiceImp userServiceImp;


    private Ticket ticket;
    private User user;
    private Movie movie;
    private MovieTime.MovieTimeLoad movieTime = MovieTime.MovieTimeLoad.TURN_01;
    private MovieTheater movieTheater;
    @BeforeEach
    public void setup(){

        user = User.builder()
                .id("user123")
                .dateOfBirth(LocalDate.of(1998, 7, 15))
                .email("adriana@gmail.com")
                .role(UserRole.ADMIN)
                .password("1234")
                .username("adriana")
                .build();

        movieTheater = new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime());

        movie = Movie.builder()
                .id("movie123")
                .name("SuperBad")
                .releaseDate(2007L)
                .description("High school seniors Seth and Evan have high hopes for a graduation party.")
                .categories(List.of(Category.CategoryLoad.COMEDY.toCategory()))
                .rooms(List.of(movieTheater))
                .duration("1h 53m")
                .posterUrl("IMAGE POSTER")
                .build();

        ticket = Ticket.builder()
                .id(1234L)
                .seat(List.of("C2", "C3", "C4"))
                .movieTime(movieTime.toMovieTime())
                .movie(movie)
                .user(user)
                .movieTheater(movieTheater)
                .build();
    }


    @Test
    public void givenId_whenGetTicketId_thenReturnTicketObject(){

        //Given
        BDDMockito.given(ticketRepository.findById(ticket.getId())).willReturn(Optional.of(ticket));

        //When
        Ticket ticketById = ticketServiceImp.getTicketById(ticket.getId());

        //Then
        Assertions.assertThat(ticketById).isEqualTo(ticket);
        Assertions.assertThat(ticketById.getId()).isEqualTo(ticket.getId());

    }

    @Test
    public void givenTicketRequest_whenCreateTicket_thenReturnTicketResponse(){

        TicketRequest ticketRequest = new TicketRequest(
                ticket.getUser().getId(),
                ticket.getMovie().getId(),
                movieTime,
                ticket.getSeat());

        //Given
        BDDMockito.given(movieServiceImp.getMovieById(ticket.getMovie().getId())).willReturn(movie);
        BDDMockito.given(movieTheaterService.getSeatByMovieAndMovieTime(any(Movie.class), any(MovieTime.class))).willReturn(movieTheater);
        BDDMockito.given(userServiceImp.getUserById(user.getId())).willReturn(user);

        //When
        TicketResponse ticketResponse = ticketServiceImp.createTicket(ticketRequest);

        //Then
        Assertions.assertThat(ticketResponse.movie_id()).isEqualTo(movie.getId());
        Assertions.assertThat(ticketResponse.user_id()).isEqualTo(user.getId());
    }

    @Test
    public void given_whenGetAllTickets_thenReturnTicketResponseList(){

        //Given
        BDDMockito.given(ticketRepository.findAll()).willReturn(List.of(ticket));

        //When
        List<TicketResponse> ticketsResponse = ticketServiceImp.getAllTickets();

        //Then
        Assertions.assertThat(ticketsResponse.size()).isEqualTo(1);
        Assertions.assertThat(ticketsResponse.getFirst().user_id()).isEqualTo(ticket.getUser().getId());
        Assertions.assertThat(ticketsResponse.getFirst().movie_id()).isEqualTo(ticket.getMovie().getId());
    }

    @Test
    public void givenTicketRequestUpdate_whenUpdateTicket_thenReturnTicketResponse(){
        TicketRequestUpdate ticketReqUpdate = new TicketRequestUpdate(
                ticket.getId(),
                ticket.getMovie().getId(),
                movieTime,
                ticket.getSeat()
        );

        Ticket newTicket = ticket;

        newTicket.setSeat(List.of("F4", "F5", "F6", "F7"));

        //Given
        BDDMockito.given(movieServiceImp.getMovieById(ticketReqUpdate.movieId())).willReturn(movie);
        BDDMockito.given(movieTheaterService.getSeatByMovieAndMovieTime(any(Movie.class), any(MovieTime.class))).willReturn(movieTheater);
        BDDMockito.given(ticketRepository.findById(ticketReqUpdate.id())).willReturn(Optional.of(ticket));

        //When
        TicketResponse ticketResponse = ticketServiceImp.updateTicket(ticketReqUpdate);

        //Then
        Assertions.assertThat(ticketResponse.id()).isEqualTo(ticket.getId());
        Assertions.assertThat(ticketResponse.movie_id()).isEqualTo(ticket.getMovie().getId());
        Assertions.assertThat(ticketResponse.user_id()).isEqualTo(ticket.getUser().getId());
    }

    @Test
    public void givenTicketId_whenDeleteTicketById_thenReturnStringMessage(){

        //Given
        BDDMockito.given(ticketRepository.findById(ticket.getId())).willReturn(Optional.of(ticket));

        //When
        String message = ticketServiceImp.deleteTicketById(ticket.getId());

        //Then
        Assertions.assertThat(message).isEqualTo("Ticket was deleted successfully");
    }

    @Test
    public void given_whenDeleteAllTickets_thenReturnStringMessage(){

        //Given
        BDDMockito.given(ticketRepository.findAll()).willReturn(List.of(ticket));

        //When
        String message = ticketServiceImp.deleteAllTickets();

        //Then
        Assertions.assertThat(message).isEqualTo("All Tickets was deleted");
    }
}
