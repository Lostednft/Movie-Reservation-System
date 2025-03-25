package system.movie_reservation.runnable.Component;

import org.springframework.stereotype.Component;
import system.movie_reservation.model.movie.EnumLoader.Category;
import system.movie_reservation.repository.unservices.CategoryRepository;

import java.util.Arrays;

@Component
public class CategoryLoader {

    private final CategoryRepository categoryRepository;

    public CategoryLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveAllCategories(){

        if(categoryRepository.findAll().size() < 14)
            Arrays.stream(Category.CategoryLoad.values())
                .map(Category.CategoryLoad::toCategory)
                .forEach(categoryRepository::save);
    }
}
