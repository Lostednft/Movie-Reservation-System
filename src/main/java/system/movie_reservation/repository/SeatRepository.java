package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.Seat;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, String> {
}
