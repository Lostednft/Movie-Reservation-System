package system.movie_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seat_tickets_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String seatKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticketValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    public SeatTicket(String seatKey, Ticket ticket, Seat seat) {
        this.seatKey = seatKey;
        this.ticketValue = ticket;
        this.seat = seat;
    }
}
