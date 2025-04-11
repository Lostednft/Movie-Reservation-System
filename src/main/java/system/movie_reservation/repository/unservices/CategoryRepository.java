package system.movie_reservation.repository.unservices;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.movie.EnumLoader.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
