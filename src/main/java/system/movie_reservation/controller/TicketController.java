package system.movie_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.ticket.TicketRequestUpdate;
import system.movie_reservation.service.TicketServiceImp;
import system.movie_reservation.service.usescases.TicketUsesCases;

@RestController
@RequestMapping("ticket")
public class TicketController {

    private final TicketUsesCases ticketUsesCases;

    public TicketController(TicketServiceImp ticketUsesCases) {
        this.ticketUsesCases = ticketUsesCases;
    }

    @PostMapping
    public ResponseEntity saveTicket(@RequestBody TicketRequest ticket){
        return ResponseEntity.ok(ticketUsesCases.createTicket(ticket));
    }

    @GetMapping
    public ResponseEntity getAllTickets(){
        return ResponseEntity.ok(ticketUsesCases.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity getTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketUsesCases.getTicketById(id));
    }

    @PutMapping
    public ResponseEntity updateTicket(@RequestBody TicketRequestUpdate ticketReqUpdate){
        return ResponseEntity.ok(ticketUsesCases.updateTicket(ticketReqUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketUsesCases.deleteTicketById(id));
    }

    @DeleteMapping
    public ResponseEntity deleteAllTickets(){
        return ResponseEntity.ok(ticketUsesCases.deleteAllTickets());
    }
}
