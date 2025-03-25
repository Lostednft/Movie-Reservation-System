package system.movie_reservation.runnable;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import system.movie_reservation.runnable.Component.CategoryLoader;
import system.movie_reservation.runnable.Component.MovieTimeLoader;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryLoader categoryLoader;
    private final MovieTimeLoader movieTimeLoader;

    public DataLoader(CategoryLoader categoryLoader, MovieTimeLoader movieTimeLoader) {
        this.categoryLoader = categoryLoader;
        this.movieTimeLoader = movieTimeLoader;
    }

    @Override
    public void run(String... args) throws Exception {

        categoryLoader.saveAllCategories();
        movieTimeLoader.saveAllMovietimes();
    }
}
