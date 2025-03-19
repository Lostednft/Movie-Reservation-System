package system.movie_reservation.model.dto;

import system.movie_reservation.model.Enums.MovieTime;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.User;

import java.util.List;

public record TicketDto(String userId,
                        String movieId,
                        MovieTime.MovieTimeLoad movieTime,
                        List<String> seat) {
}
