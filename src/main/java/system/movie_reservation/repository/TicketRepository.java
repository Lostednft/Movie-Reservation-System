package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
