package system.movie_reservation.model.dto;

import system.movie_reservation.model.Enums.Category;

import java.util.List;

public record MovieRequest(String id,
                           String name,
                           String description,
                           List<Category.CategoryLoad> categories,
                           String posterUrl,
                           String duration) {
}
