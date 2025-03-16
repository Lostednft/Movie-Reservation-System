package system.movie_reservation.runnable;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import system.movie_reservation.model.Enums.Category;
import system.movie_reservation.repository.CategoryRepository;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryService;

    public DataLoader(CategoryRepository categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {

        Arrays.stream(Category.CategoryLoad.values())
                .map(Category.CategoryLoad::toCategory)
                .forEach(categoryService::save);
    }
}
