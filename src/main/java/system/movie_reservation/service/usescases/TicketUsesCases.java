package system.movie_reservation.service.usescases;

import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.ticket.TicketRequestUpdate;
import system.movie_reservation.model.ticket.TicketResponse;

import java.util.List;

public interface TicketUsesCases {

    public Ticket getTicketById(Long id);

    public TicketResponse createTicket(TicketRequest ticketRequest);

    public List<TicketResponse> geAllTickets();

    public TicketResponse updateTicket(TicketRequestUpdate ticketReqUpdate);

    public String deleteTicketById(Long id);

    public String deleteAllTickets();
}
