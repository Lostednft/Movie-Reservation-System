package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.movie_reservation.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {


}
