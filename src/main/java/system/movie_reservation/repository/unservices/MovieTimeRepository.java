package system.movie_reservation.repository.unservices;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;

public interface MovieTimeRepository extends JpaRepository<MovieTime, Integer> {
}
