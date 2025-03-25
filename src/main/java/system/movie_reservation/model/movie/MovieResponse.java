package system.movie_reservation.model.movie;

import system.movie_reservation.model.movie.EnumLoader.Category;
import system.movie_reservation.model.seat.Seat;

import java.util.List;

public record MovieResponse(String id,
                            String name,
                            Long releaseDate,
                            String description,
                            String posterUrl,
                            String duration,
                            List<Long> rooms_id,
                            List<String> categories) {

    public MovieResponse(Movie movie) {
        this(
            movie.getId(),
            movie.getName(),
            movie.getReleaseDate(),
            movie.getDescription(),
            movie.getPosterUrl(),
            movie.getDuration(),
            movie.getRooms().stream().map(Seat::getId).toList(),
            movie.getCategories().stream().map(Category::getCategory).toList()
        );
    }
}
