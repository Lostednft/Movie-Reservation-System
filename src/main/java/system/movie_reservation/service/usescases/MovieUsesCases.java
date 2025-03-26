package system.movie_reservation.service.usescases;

import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.movie.MovieRequest;
import system.movie_reservation.model.movie.MovieRequestUpdate;
import system.movie_reservation.model.movie.MovieResponse;

import java.util.List;

public interface MovieUsesCases {

    Movie getMovieById(String id);
    MovieResponse createMovie(MovieRequest movieRequest);
    MovieResponse updateMovie(MovieRequestUpdate movieReqUpdate);
    List<MovieResponse> findAllMovies();
    String removeMovieById(String id);
    String removeAllMovies();


}
