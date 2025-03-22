package system.movie_reservation.runnable;

import org.springframework.stereotype.Component;
import system.movie_reservation.model.Enum.Category;
import system.movie_reservation.repository.CategoryRepository;

import java.util.Arrays;

@Component
public class CategoryLoader {

    private final CategoryRepository categoryRepository;

    public CategoryLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveAllCategories(){
        Arrays.stream(Category.CategoryLoad.values())
                .map(Category.CategoryLoad::toCategory)
                .forEach(categoryRepository::save);
    }
}
