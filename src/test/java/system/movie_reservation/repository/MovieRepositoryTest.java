package system.movie_reservation.repository;

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

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;
    private Movie movie;

    @BeforeEach
    public void setup(){

        List<MovieTheater> movieTheaterList = List.of(
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_02.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_03.toMovieTime()));

        movie = Movie.builder()
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
    public void givenId_whenFindMovieById_thenReturnMovieObject(){

        //Given
        movieRepository.save(movie);

        //When
        Movie movieById = movieRepository.findById(movie.getId()).get();

        //Then
        Assertions.assertThat(movieById).isEqualTo(movie);
        Assertions.assertThat(movieById.getId()).isEqualTo(movie.getId());
    }

    @Test
    public void given_whenFindAll_thenReturnMovieList(){

        //Given
        movieRepository.save(movie);

        //When
        List<Movie> allMovies = movieRepository.findAll();

        //Then
        Assertions.assertThat(allMovies).contains(movie);
        Assertions.assertThat(allMovies.getLast()).isEqualTo(movie);
    }

    @Test
    public void givenId_whenDeleteMovieById_thenReturnMovieObjectRemoved(){

        //Given
        movieRepository.save(movie);

        //When
        movieRepository.deleteById(movie.getId());
        List<Movie> allMovies = movieRepository.findAll();
        //Then
        Assertions.assertThat(allMovies).doesNotContain(movie);
    }

    @Test
    public void given_whenDeleteAllMovies_thenReturnEmptyList(){

        //Given
        movieRepository.save(movie);

        //When
        movieRepository.deleteAll();
        List<Movie> allMovies = movieRepository.findAll();

        //Then
        Assertions.assertThat(allMovies).isEmpty();
    }
}
