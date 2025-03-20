package system.movie_reservation.model.dto;

import system.movie_reservation.model.Enums.Category;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.Seat;

import java.util.List;

public record MovieResponse(String id,
                            String name,
                            Long releaseDate,
                            String description,
                            String posterUrl,
                            String duration,
                            List<Integer> rooms_id,
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
