package system.movie_reservation.model.movie;

import system.movie_reservation.model.movie.EnumLoader.Category;

import java.util.List;

public record MovieRequest(String name,
                           Long releaseDate,
                           String description,
                           List<Category.CategoryLoad> categories,
                           String posterUrl,
                           String duration) {
}
