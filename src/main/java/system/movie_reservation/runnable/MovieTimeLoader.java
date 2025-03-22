package system.movie_reservation.runnable;

import org.springframework.stereotype.Component;
import system.movie_reservation.model.Enum.MovieTime;
import system.movie_reservation.repository.MovieTimeRepository;

import java.util.Arrays;

@Component
public class MovieTimeLoader {

    private final MovieTimeRepository movieTimeRepository;

    public MovieTimeLoader(MovieTimeRepository movieTimeRepository) {
        this.movieTimeRepository = movieTimeRepository;
    }

    public void saveAllMovietimes(){

        Arrays.stream(MovieTime.MovieTimeLoad.values())
                .map(MovieTime.MovieTimeLoad::toMovieTime)
                .forEach(movieTimeRepository::save);
    }
}
