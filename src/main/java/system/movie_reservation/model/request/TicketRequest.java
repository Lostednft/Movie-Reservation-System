package system.movie_reservation.model.request;

import system.movie_reservation.model.Enum.MovieTime;
import java.util.List;

public record TicketRequest(String userId,
                            String movieId,
                            MovieTime.MovieTimeLoad movieTime,
                            List<String> seat) {
}
