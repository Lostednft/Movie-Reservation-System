package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.exception.ValidateException;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.dto.MovieRequest;
import system.movie_reservation.repository.MovieRepository;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie getMovieById(String id){
        return movieRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No movie with this ID was found."));
    }

    public Movie createMovie(MovieRequest movieRequest){
        ValidateException.checkFieldsEmpty(movieRequest);
        Movie movie = new Movie(movieRequest);
        movieRepository.save(movie);
        return movie;
    }

    public Movie updateMovie(MovieRequest movie){

        Movie movieToUpdate = new Movie(movie);
        return movieRepository.save(movieToUpdate);
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public void removeMovieById(String id){

        Movie movie = getMovieById(id);
        if(movie != null)
            movieRepository.delete(movie);
    }

    public void removeAllMovies(){
        movieRepository.deleteAll();
    }
}
