package system.movie_reservation.RepositoryLayerTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import system.movie_reservation.model.movie.EnumLoader.Category;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.repository.MovieRepository;
import system.movie_reservation.repository.TicketRepository;
import system.movie_reservation.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketRepositoryLayerTest {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    private Ticket ticket;
    private Movie movie;
    private User user;

    @BeforeEach
    public void setup(){
        user = User.builder()
                .dateOfBirth(LocalDate.of(1998, 7, 15))
                .email("adriana@gmail.com")
                .role(UserRole.ADMIN)
                .password("1234")
                .username("adriana")
                .build();

        MovieTheater movieTheater = new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime());

        movie = Movie.builder()
                .name("SuperBad")
                .releaseDate(2007L)
                .description("High school seniors Seth and Evan have high hopes for a graduation party.")
                .categories(List.of(Category.CategoryLoad.COMEDY.toCategory()))
                .rooms(List.of(movieTheater))
                .duration("1h 53m")
                .posterUrl("IMAGE POSTER")
                .build();

        ticket = Ticket.builder()
                .seat(List.of("C2", "C3", "C4"))
                .movieTime(MovieTime.MovieTimeLoad.TURN_01.toMovieTime())
                .movie(movie)
                .user(user)
                .movieTheater(movieTheater)
                .build();
    }

    @Test
    public void givenId_whenFindById_thenReturnTicketObject(){

        //Given
        ticketRepository.save(ticket);

        //When
        Ticket ticketFound = ticketRepository.findById(ticket.getId()).get();

        //Then
        Assertions.assertThat(ticketFound).isEqualTo(ticket);
        Assertions.assertThat(ticketFound.getMovie()).isEqualTo(ticket.getMovie());
        Assertions.assertThat(ticketFound.getUser()).isEqualTo(ticket.getUser());
    }

    @Test
    public void given_whenFindAll_thenReturnTicketsList(){
        //Given
        userRepository.save(user);
        movieRepository.save(movie);
        ticketRepository.save(ticket);

        //When
        List<Ticket> allTickets = ticketRepository.findAll();

        //Then
        Assertions.assertThat(allTickets).isNotEmpty();
        Assertions.assertThat(allTickets).contains(ticket);
    }


    @Test
    public void givenId_whenDeleteTicketById_thenReturnTicketDeleted(){
        //Given
        ticketRepository.save(ticket);

        //When
        ticketRepository.deleteById(ticket.getId());
        List<Ticket> allTickets = ticketRepository.findAll();

        //Then
        Assertions.assertThat(allTickets).doesNotContain(ticket);
    }

    @Test
    public void given_whenDeleteAllTickets_thenReturnEmptyList(){
        //Given
        ticketRepository.save(ticket);
        movieRepository.save(movie);
        userRepository.save(user);

        //When
        ticketRepository.deleteAll();
        List<Ticket> allTickets = ticketRepository.findAll();

        //Then
        Assertions.assertThat(allTickets).isEmpty();
    }
}
