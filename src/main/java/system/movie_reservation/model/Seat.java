package system.movie_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.Enum.MovieTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seats_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    private MovieTime movieTime;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatTicket> seatTicket = new ArrayList<>();

    public Seat(Movie movie, MovieTime movieTime) {
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