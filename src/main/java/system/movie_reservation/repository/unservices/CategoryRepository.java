package system.movie_reservation.repository.unservices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.movie_reservation.model.movie.EnumLoader.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
