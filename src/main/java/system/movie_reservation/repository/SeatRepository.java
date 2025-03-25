package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.Seat;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> findByMovieAndMovieTime(Movie movie, MovieTime movieTime);
}
