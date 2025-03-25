package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.movie_reservation.model.Movie.EnumLoader.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
