package system.movie_reservation.model.request.ToUpdate;

import system.movie_reservation.model.Enum.MovieTime;

import java.util.List;

public record TicketRequestUpdate(Long id,
                                  String movieId,
                                  MovieTime.MovieTimeLoad movieTime,
                                  List<String> seat) {
}
