package system.movie_reservation.model.ticket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.Seat;
import system.movie_reservation.model.user.User;

import java.util.List;

@Entity
@Table(name = "ticket_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    private MovieTime movieTime;

    private List<String> seat;

    @ManyToOne(fetch = FetchType.LAZY)
    private Seat roomSeats;

    public Ticket(User user, Movie movie, TicketRequest ticketRequest) {
        this.user = user;
        this.movie = movie;
        this.seat = ticketRequest.seat();
        this.movieTime = ticketRequest.movieTime().toMovieTime();
    }
}
