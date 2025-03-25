package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.movie.Movie;

public interface MovieRepository extends JpaRepository<Movie, String> {


}
