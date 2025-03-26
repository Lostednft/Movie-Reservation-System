package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.MovieTheater;

import java.util.Optional;

@Repository
public interface MovieTheaterRepository extends JpaRepository<MovieTheater, Long> {

    Optional<MovieTheater> findByMovieAndMovieTime(Movie movie, MovieTime movieTime);
}
