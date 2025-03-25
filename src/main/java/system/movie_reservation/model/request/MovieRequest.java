package system.movie_reservation.model.request;

import system.movie_reservation.model.Enum.Category;

import java.util.List;

public record MovieRequest(String name,
                           Long releaseDate,
                           String description,
                           List<Category.CategoryLoad> categories,
                           String posterUrl,
                           String duration) {
}
