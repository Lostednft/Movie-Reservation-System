package system.movie_reservation.model.seat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.ticket.Ticket;

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
    @JoinColumn(name = "movie_theater_id")
    private MovieTheater movieTheater;

    public SeatTicket(String seatKey, Ticket ticket, MovieTheater movieTheater) {
        this.seatKey = seatKey;
        this.ticketValue = ticket;
        this.movieTheater = movieTheater;
    }
}
