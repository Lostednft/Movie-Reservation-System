package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.ticket.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
