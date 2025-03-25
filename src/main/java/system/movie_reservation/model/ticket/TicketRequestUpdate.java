package system.movie_reservation.model.ticket;

import system.movie_reservation.model.movie.EnumLoader.MovieTime;

import java.util.List;

public record TicketRequestUpdate(Long id,
                                  String movieId,
                                  MovieTime.MovieTimeLoad movieTime,
                                  List<String> seat) {
}
