package system.movie_reservation.model.seat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_theater_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieTheater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    private MovieTime movieTime;

    @OneToMany(mappedBy = "movieTheater", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatTicket> seatTicket = new ArrayList<>();

    public MovieTheater(Movie movie, MovieTime movieTime) {
        this.movie = movie;
        this.movieTime = movieTime;
        this.seatTicket = constructorSeats();
    }

    private List<SeatTicket> constructorSeats(){

        char queue = 'A';

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                seatTicket.add(new SeatTicket("" + queue + j, null, this));
            }
        queue += 1;
        }
        return seatTicket;
    }
}