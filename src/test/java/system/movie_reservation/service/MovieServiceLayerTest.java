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
import system.movie_reservation.model.movie.MovieRequest;
import system.movie_reservation.model.movie.MovieRequestUpdate;
import system.movie_reservation.model.movie.MovieResponse;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MovieServiceLayerTest {

    @InjectMocks
    private MovieServiceImp movieServiceImp;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieTheaterService movieTheaterService;

    @Mock
    private SeatTicketService seatTicketService;

    private Movie movie;

    private List<MovieTheater> movieTheaterList;


    @BeforeEach
    public void setup(){

        movieTheaterList = List.of(
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_02.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_03.toMovieTime()));

        movie = Movie.builder()
                .id("1234")
                .name("SuperBad")
                .releaseDate(2007L)
                .description("High school seniors Seth and Evan have high hopes for a graduation party.")
                .categories(List.of(Category.CategoryLoad.COMEDY.toCategory()))
                .rooms(movieTheaterList)
                .duration("1h 53m")
                .posterUrl("IMAGE POSTER")
                .build();
    }

    @Test
    public void givenId_whenGetMovieById_thenMovieObject(){

        //Given
        BDDMockito.given(movieRepository.findById(movie.getId())).willReturn(Optional.of(movie));

        //When
        Movie movieFound = movieRepository.findById(movie.getId()).get();

        //Then
        Assertions.assertThat(movieFound).isEqualTo(movie);
        Assertions.assertThat(movieFound.getId()).isEqualTo(movie.getId());
    }

    @Test
    public void givenMovieRequest_whenCreateMovie_thenReturnMovieResponse(){


        MovieRequest movieRequest = new MovieRequest(
                movie.getName(),
                movie.getReleaseDate(),
                movie.getDescription(),
                List.of(Category.CategoryLoad.COMEDY),
                movie.getPosterUrl(),
                movie.getDuration());

        //Given
        BDDMockito.given(movieRepository.save(any(Movie.class))).willReturn(movie);
        BDDMockito.given(movieTheaterService.createSeatsToMovie(any(Movie.class))).willReturn(movieTheaterList);

        //When
        MovieResponse movieResponse = movieServiceImp.createMovie(movieRequest);

        //Then
        Assertions.assertThat(movieResponse).isNotNull();
        Assertions.assertThat(movieResponse.name()).isEqualTo(movie.getName());
        Assertions.assertThat(movieResponse.duration()).isEqualTo(movie.getDuration());
    }

    @Test
    public void givenMovie_whenUpdateMovie_thenReturnMovieResponse(){

        MovieRequestUpdate movieReqUpdate = new MovieRequestUpdate(
                movie.getId(),
                movie.getName(),
                movie.getReleaseDate(),
                movie.getDescription(),
                List.of(Category.CategoryLoad.COMEDY),
                movie.getPosterUrl(),
                movie.getDuration()
        );

        //Given
        BDDMockito.given(movieRepository.findById(movie.getId())).willReturn(Optional.of(movie));
        BDDMockito.given(movieRepository.save(any(Movie.class))).willReturn(movie);

        //When
        MovieResponse movieResponse = movieServiceImp.updateMovie(movieReqUpdate);

        //Then
        Assertions.assertThat(movieResponse).isNotNull();
        Assertions.assertThat(movieResponse.id()).isEqualTo(movie.getId());
        Assertions.assertThat(movieResponse.duration()).isEqualTo(movie.getDuration());
    }

    @Test
    public void given_whenFindAllMovies_thenReturnListMovieResponse(){

        //Given
        BDDMockito.given(movieRepository.findAll()).willReturn(List.of(movie));
        BDDMockito.given(movieRepository.save(movie)).willReturn(movie);

        //When
        movieRepository.save(movie);
        List<MovieResponse> allMovies = movieServiceImp.findAllMovies();

        //Then
        Assertions.assertThat(allMovies).isNotEmpty();
        Assertions.assertThat(allMovies.getLast().name()).contains(movie.getName());
        Assertions.assertThat(allMovies.getLast().id()).contains(movie.getId());
    }


    @Test
    public void givenId_whenRemoveMovieById_thenReturnMessageApproved(){


        //Given
        BDDMockito.willDoNothing().given(movieRepository).delete(movie);
        BDDMockito.given(movieRepository.findById(movie.getId())).willReturn(Optional.of(movie));

        //When
        String response = movieServiceImp.removeMovieById(movie.getId());

        //Then
        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(response).isEqualTo("Movie deleted successfully");
    }

    @Test
    public void given_whenRemoveAllMovies_thenReturnStringMessage(){

        //Given
        BDDMockito.given(movieRepository.findAll()).willReturn(List.of(movie));
        BDDMockito.willDoNothing().given(seatTicketService).deleteAllTicketsFromSeat();
        BDDMockito.willDoNothing().given(movieRepository).deleteAll();

        //When
        String message = movieServiceImp.removeAllMovies();

        //Then
        Assertions.assertThat(message).isNotEmpty();
        Assertions.assertThat(message).isEqualTo("All movies was removed successfully");
    }
}
