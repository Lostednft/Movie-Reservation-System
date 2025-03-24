package system.movie_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation.model.request.TicketRequest;
import system.movie_reservation.model.request.ToUpdate.TicketRequestUpdate;
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

    @GetMapping
    public ResponseEntity getAllTickets(){
        return ResponseEntity.ok(ticketService.geAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity getTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PutMapping
    public ResponseEntity updateTicket(@RequestBody TicketRequestUpdate ticketReqUpdate){
        return ResponseEntity.ok(ticketService.updateTicket(ticketReqUpdate));
    }

}
