package system.movie_reservation.service.usescases;

import system.movie_reservation.model.ticket.Ticket;

public interface TicketUsesCases {

    public Ticket getTicketById(Long id);
}
