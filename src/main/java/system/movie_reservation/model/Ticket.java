package system.movie_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.Enums.MovieTime;
import system.movie_reservation.model.dto.TicketDto;

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
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private MovieTime movieTime;

    private List<String> seat;

    public Ticket(User user, Movie movie, TicketDto ticketDto) {
        this.user = user;
        this.movie = movie;
        this.movieTime = ticketDto.movieTime().toMovieTime();
        this.seat = ticketDto.seat();
    }
}
