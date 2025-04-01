package system.movie_reservation.service.usescases;

import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.ticket.TicketRequestUpdate;
import system.movie_reservation.model.ticket.TicketResponse;

import java.util.List;

public interface TicketUsesCases {

    Ticket getcketById(Long id);
    TicketResponse createTicket(TicketRequest ticketRequest);
    List<TicketResponse> getAllTickets();
    TicketResponse updateTicket(TicketRequestUpdate ticketReqUpdate);
    String deleteTicketById(Long id);
    String deleteAllTickets();
}
