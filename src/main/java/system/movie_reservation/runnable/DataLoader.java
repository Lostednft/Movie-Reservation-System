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

    private final CategoryRepository categoryService;
    private final SeatRepository seatRepository;
    private final MovieTimeRepository movieTimeRepository;
    private final MovieRepository movieRepository;

    public DataLoader(CategoryRepository categoryService,
                      SeatRepository seatRepository,
                      MovieTimeRepository movieTimeRepository, MovieRepository movieRepository) {
        this.categoryService = categoryService;
        this.seatRepository = seatRepository;
        this.movieTimeRepository = movieTimeRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(Category.CategoryLoad.values())
                .map(Category.CategoryLoad::toCategory)
                .forEach(categoryService::save);

        Arrays.stream(MovieTime.MovieTimeLoad.values())
                .map(MovieTime.MovieTimeLoad::toMovieTime)
                .forEach(movieTimeRepository::save);

    }
}
