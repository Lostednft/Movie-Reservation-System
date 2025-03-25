package system.movie_reservation.runnable.Component;

import org.springframework.stereotype.Component;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.repository.unservices.MovieTimeRepository;

import java.util.Arrays;

@Component
public class MovieTimeLoader {

    private final MovieTimeRepository movieTimeRepository;

    public MovieTimeLoader(MovieTimeRepository movieTimeRepository) {
        this.movieTimeRepository = movieTimeRepository;
    }

    public void saveAllMovietimes(){

        if(movieTimeRepository.findAll().size() < 3)
            Arrays.stream(MovieTime.MovieTimeLoad.values())
                .map(MovieTime.MovieTimeLoad::toMovieTime)
                .forEach(movieTimeRepository::save);
    }
}
