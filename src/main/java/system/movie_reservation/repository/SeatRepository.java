package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
