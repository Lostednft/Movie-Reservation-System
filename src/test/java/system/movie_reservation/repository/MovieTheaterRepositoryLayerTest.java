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
public class MovieTheaterRepositoryLayerTest {

    @Autowired
    private MovieTheaterRepository movieTheaterRepository;
    private MovieTheater movieTheater;
    private Movie movie;

    @BeforeEach
    public void setup(){

        movieTheater = new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime());

        movie = Movie.builder()
                .name("SuperBad")
                .releaseDate(2007L)
                .description("High school seniors Seth and Evan have high hopes for a graduation party.")
                .categories(List.of(Category.CategoryLoad.COMEDY.toCategory()))
                .rooms(List.of(movieTheater))
                .duration("1h 53m")
                .posterUrl("IMAGE POSTER")
                .build();
    }

    @Test
    public void givenId_whenFindById_thenReturnMovieTheaterObject(){

        //Given
        movieTheaterRepository.save(movieTheater);

        //When
        MovieTheater movieTheaterFound = movieTheaterRepository.findById(movieTheater.getId()).get();

        //Then
        Assertions.assertThat(movieTheaterFound).isEqualTo(movieTheater);
    }

    @Test
    public void givenMovieAndMovieTime_whenFindByMovieAndMovieTime_thenReturnMovieTheaterObject(){

        //Given
        movieTheaterRepository.save(movieTheater);

        //When
        MovieTheater movieTheaterFound = movieTheaterRepository.findByMovieAndMovieTime(movieTheater.getMovie(), movieTheater.getMovieTime()).get();

        //Then
        Assertions.assertThat(movieTheaterFound).isEqualTo(movieTheater);
    }

    @Test
    public void givenListMovieTheater_whenSaveAll_thenReturnListMovieTheater(){

        //Given
        movieTheaterRepository.saveAll(List.of(movieTheater));

        //When
        List<MovieTheater> allMovieTheater = movieTheaterRepository.findAll();

        //Then
        Assertions.assertThat(allMovieTheater).isNotEmpty();
        Assertions.assertThat(allMovieTheater).contains(movieTheater);
        Assertions.assertThat(allMovieTheater.size()).isGreaterThan(1);
    }
}
