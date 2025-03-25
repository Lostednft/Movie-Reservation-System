package system.movie_reservation.model.movie.EnumLoader;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "movietimes_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieTime {

    @Id
    public Integer id;
    public LocalTime startTime;
    public LocalTime endTime;

    public enum MovieTimeLoad {
        TURN_01(1, LocalTime.of(14, 0), LocalTime.of(17, 0)),
        TURN_02(2, LocalTime.of(17, 30), LocalTime.of(20, 30)),
        TURN_03(3, LocalTime.of(21, 0), LocalTime.of(0, 0));

        private Integer id;
        private LocalTime startTime;
        private LocalTime endTime;

        MovieTimeLoad(Integer id, LocalTime startTime, LocalTime endTime) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public MovieTime toMovieTime(){
            return new MovieTime(id, startTime, endTime);
        }
    }
}
