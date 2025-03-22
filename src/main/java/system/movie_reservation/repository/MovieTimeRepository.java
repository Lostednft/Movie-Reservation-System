package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.Enum.MovieTime;

public interface MovieTimeRepository extends JpaRepository<MovieTime, Integer> {
}
