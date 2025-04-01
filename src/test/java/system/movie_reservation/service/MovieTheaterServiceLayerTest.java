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
import system.movie_reservation.repository.MovieTheaterRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MovieTheaterServiceLayerTest {

    @InjectMocks
    private MovieTheaterService movieTheaterService;

    @Mock
    private MovieTheaterRepository movieTheaterRepository;

    private Movie movie;

    private MovieTime movieTime;

    private List<MovieTheater> movieTheaterList;


    @BeforeEach
    public void setup(){

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


        movieTime = MovieTime.MovieTimeLoad.TURN_02.toMovieTime();
    }

    @Test
    public void givenMovieAndMovieTime_whenGetSeatByMovieAndMovieTime_thenReturnMovieTheater(){

        movie.setRooms(movieTheaterList);

        //Given
        BDDMockito.given(movieTheaterRepository.findByMovieAndMovieTime(movie, movieTime)).willReturn(Optional.ofNullable(movieTheaterList.get(1)));

        //When
        MovieTheater movieTheaterFound = movieTheaterService.getSeatByMovieAndMovieTime(movie, movieTime);

        //Then
        Assertions.assertThat(movieTheaterFound).isEqualTo(movieTheaterList.get(1));
        Assertions.assertThat(movieTheaterFound.getMovie()).isEqualTo(movie);
    }

    @Test
    public void givenMovie_whenCreateSeatsToMovie_thenListMovieTheater(){

        //Given
        BDDMockito.given(movieTheaterRepository.saveAll(any())).willReturn(movieTheaterList);

        //When
        List<MovieTheater> seatsToMovie = movieTheaterService.createSeatsToMovie(movie);

        //Then
        Assertions.assertThat(seatsToMovie.getFirst().getMovie()).isEqualTo(movie);
        Assertions.assertThat(seatsToMovie.size()).isEqualTo(movieTheaterList.size());
    }
}
