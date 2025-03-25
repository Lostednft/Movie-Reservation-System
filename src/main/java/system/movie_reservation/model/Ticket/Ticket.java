package system.movie_reservation.model.Ticket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.Movie.EnumLoader.MovieTime;
import system.movie_reservation.model.Movie.Movie;
import system.movie_reservation.model.Seat.Seat;
import system.movie_reservation.model.User.User;

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
