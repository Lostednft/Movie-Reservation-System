package system.movie_reservation.model.Ticket;

import system.movie_reservation.model.Movie.EnumLoader.MovieTime;

import java.util.List;

public record TicketRequestUpdate(Long id,
                                  String movieId,
                                  MovieTime.MovieTimeLoad movieTime,
                                  List<String> seat) {
}
