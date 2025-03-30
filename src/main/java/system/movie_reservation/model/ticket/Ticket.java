package system.movie_reservation.model.ticket;

import jakarta.persistence.*;
import lombok.*;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.model.user.User;

import java.util.List;

@Entity
@Table(name = "ticket_tb")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    private MovieTime movieTime;

    private List<String> seat;

    @ManyToOne(fetch = FetchType.LAZY)
    private MovieTheater movieTheater;

    public Ticket(User user, Movie movie, TicketRequest ticketRequest) {
        this.user = user;
        this.movie = movie;
        this.seat = ticketRequest.seat();
        this.movieTime = ticketRequest.movieTime().toMovieTime();
    }


    public Ticket(User user, Movie movie, MovieTheater room, TicketRequestUpdate ticketReqUpdate) {
        this.id = ticketReqUpdate.id();
        this.user = user;
        this.movie = movie;
        this.movieTime = ticketReqUpdate.movieTime().toMovieTime();
        this.seat = ticketReqUpdate.seat();
        this.movieTheater = room;
    }
}
