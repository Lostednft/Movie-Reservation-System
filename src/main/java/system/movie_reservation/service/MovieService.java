package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.dto.MovieRequest;
import system.movie_reservation.repository.MovieRepository;


@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie getMovieById(String id){
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("No movie found with this id!"));
    }

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie createMovie(MovieRequest movieRequest){

        Movie movie = new Movie(movieRequest);
        movieRepository.save(movie);

        return movie;
    }

    public Movie updateMovie(MovieRequest movie){

        Movie movieToUpdate = new Movie(movie);
        return movieRepository.save(movieToUpdate);
    }
}
