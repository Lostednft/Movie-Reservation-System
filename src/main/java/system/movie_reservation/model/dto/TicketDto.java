package system.movie_reservation.model.dto;

import system.movie_reservation.model.Enums.MovieTime;

import java.util.List;

public record TicketDto(String userId,
                        String movieId,
                        MovieTime.MovieTimeLoad movieTime,
                        List<String> seat) {
}
