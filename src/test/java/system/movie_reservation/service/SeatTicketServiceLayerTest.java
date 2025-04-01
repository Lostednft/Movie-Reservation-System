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
import system.movie_reservation.model.seat.SeatTicket;
import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.repository.MovieTheaterRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SeatTicketServiceLayerTest {

    @InjectMocks
    private SeatTicketService seatTicketService;

    @Mock
    private MovieTheaterRepository movieTheaterRepository;

    private Ticket ticket;
    private User maria;
    private Movie movie;
    private List<MovieTheater> movieTheaterList;

    @BeforeEach
    public void setup(){

        maria = User.builder()
                .id("15")
                .username("maria")
                .email("maria@gmail.com")
                .role(UserRole.ADMIN)
                .password("1234")
                .dateOfBirth(LocalDate.of(1998, 1, 15))
                .build();

        movie = Movie.builder()
                .id("1234")
                .name("SuperBad")
                .releaseDate(2007L)
                .description("High school seniors Seth and Evan have high hopes for a graduation party.")
                .categories(List.of(Category.CategoryLoad.COMEDY.toCategory()))
                .duration("1h 53m")
                .posterUrl("IMAGE POSTER")
                .build();

        movieTheaterList = List.of(
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_02.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_03.toMovieTime()));

        movie.setRooms(movieTheaterList);


        ticket = Ticket.builder()
                .id(1234L)
                .seat(List.of("A4", "A5", "A6"))
                .user(maria)
                .movie(movie)
                .movieTheater(movieTheaterList.getLast())
                .movieTime(MovieTime.MovieTimeLoad.TURN_03.toMovieTime())
                .build();

    }

    @Test
    public void givenTicket_whenSaveSeatWithTickets_thenReturnVoid(){

        //Given
        BDDMockito.given(movieTheaterRepository.save(ticket.getMovieTheater())).willReturn(ticket.getMovieTheater());

        //When
        seatTicketService.saveSeatWithTickets(ticket);

        //Then
        List<String> keySeatTicketList = new ArrayList<>();

        ticket.getMovieTheater().getSeatTicket().stream().filter(
                s -> s.getTicketValue() != null &&
                        s.getTicketValue().equals(ticket))
                .map(SeatTicket::getSeatKey)
                .forEach(keySeatTicketList::add);

        Assertions.assertThat(keySeatTicketList).isEqualTo(ticket.getSeat());
        Assertions.assertThat(keySeatTicketList.size()).isEqualTo(3);
        Assertions.assertThat(keySeatTicketList.getFirst()).isEqualTo(ticket.getSeat().getFirst());
    }

    @Test
    public void givenOldTicketAndNewTicket_whenUpdateTicketFromSeat_thenReturnVoid(){

        Ticket newTicket = ticket;
        newTicket.setSeat(List.of("C4", "C5", "C6"));

        //Given
        BDDMockito.given(movieTheaterRepository.save(any(MovieTheater.class))).willReturn(any(MovieTheater.class));

        //When
        seatTicketService.updateTicketFromSeat(ticket,newTicket);

        List<String> keySeatTicketList = new ArrayList<>();

        ticket.getMovieTheater().getSeatTicket().stream().filter(
                        s -> s.getTicketValue() != null &&
                                s.getTicketValue().equals(newTicket))
                .map(SeatTicket::getSeatKey)
                .forEach(keySeatTicketList::add);

        //Then
        Assertions.assertThat(keySeatTicketList).isEqualTo(newTicket.getSeat());
        Assertions.assertThat(keySeatTicketList.size()).isEqualTo(3);
        Assertions.assertThat(keySeatTicketList.getFirst()).isEqualTo(newTicket.getSeat().getFirst());
    }

    @Test
    public void givenTicket_whenRemoveTicketFromSeat_thenReturnVoid() {

        //Given
        BDDMockito.given(movieTheaterRepository.save(any(MovieTheater.class))).willReturn(any(MovieTheater.class));

        //When
        seatTicketService.removeTicketFromSeat(ticket);

        List<SeatTicket> newSeatTicketList = ticket.getMovieTheater().getSeatTicket().stream()
                .filter(s -> s.getTicketValue() != null)
                .toList();

        //Then
        Assertions.assertThat(newSeatTicketList).isEmpty();
    }

    @Test
    public void given_whenDeleteAllTicketsFromSeat_thenReturnVoid() {

        //Given
        BDDMockito.given(movieTheaterRepository.findAll()).willReturn(movieTheaterList);
        BDDMockito.given(movieTheaterRepository.saveAll(movieTheaterList)).willReturn(movieTheaterList);

        //When
        seatTicketService.deleteAllTicketsFromSeat();
        List<MovieTheater> allMoviesTheater = movieTheaterRepository.findAll();
        List<SeatTicket> seatTicketList = new ArrayList<>();
        for (MovieTheater movieTheater : allMoviesTheater) {
            seatTicketList = movieTheater.getSeatTicket().stream().filter(s -> s.getTicketValue() != null)
                    .toList();
        }

        //Then
        Assertions.assertThat(seatTicketList).isEmpty();
    }

}
