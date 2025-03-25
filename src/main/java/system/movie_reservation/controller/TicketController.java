package system.movie_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.ticket.TicketRequestUpdate;
import system.movie_reservation.service.TicketServiceImp;

@RestController
@RequestMapping("ticket")
public class TicketController {

    private final TicketServiceImp ticketServiceImp;

    public TicketController(TicketServiceImp ticketServiceImp) {
        this.ticketServiceImp = ticketServiceImp;
    }

    @PostMapping
    public ResponseEntity saveTicket(@RequestBody TicketRequest ticket){
        return ResponseEntity.ok(ticketServiceImp.createTicket(ticket));
    }

    @GetMapping
    public ResponseEntity getAllTickets(){
        return ResponseEntity.ok(ticketServiceImp.geAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity getTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketServiceImp.getTicketById(id));
    }

    @PutMapping
    public ResponseEntity updateTicket(@RequestBody TicketRequestUpdate ticketReqUpdate){
        return ResponseEntity.ok(ticketServiceImp.updateTicket(ticketReqUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketServiceImp.deleteTicketById(id));
    }

    @DeleteMapping
    public ResponseEntity deleteAllTickets(){
        return ResponseEntity.ok(ticketServiceImp.deleteAllTickets());
    }
}
