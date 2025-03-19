package system.movie_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.bcel.BcelCflowStackFieldAdder;
import system.movie_reservation.model.Enums.MovieTime;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "seats_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Movie movie;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private MovieTime movieTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, Ticket> ticketsAvailable = new HashMap<>();


    public Seat(Movie movie, MovieTime movieTime) {
        this.movie = movie;
        this.movieTime = movieTime;
        this.ticketsAvailable = constructorSeats();
    }


    private Map<String, Ticket> constructorSeats(){

        char queue = 'A';

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ticketsAvailable.put("" + queue + 1, null);
            }
            queue += 1;
        }
        return ticketsAvailable;
    }
}
