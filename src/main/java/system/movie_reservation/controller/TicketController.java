package system.movie_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.movie_reservation.model.dto.TicketRequest;
import system.movie_reservation.service.TicketService;

@RestController
@RequestMapping("ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity saveTicket(@RequestBody TicketRequest ticket){
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }
}
