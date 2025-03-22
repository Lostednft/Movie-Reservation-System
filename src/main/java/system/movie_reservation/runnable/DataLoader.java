package system.movie_reservation.runnable;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import system.movie_reservation.model.Enums.Category;
import system.movie_reservation.model.Enums.MovieTime;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.Seat;
import system.movie_reservation.repository.CategoryRepository;
import system.movie_reservation.repository.MovieRepository;
import system.movie_reservation.repository.MovieTimeRepository;
import system.movie_reservation.repository.SeatRepository;

import java.util.Arrays;

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
