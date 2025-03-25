package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.exception.MovieValidateHandler;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.movie.MovieRequest;
import system.movie_reservation.model.movie.MovieRequestUpdate;
import system.movie_reservation.model.movie.MovieResponse;
import system.movie_reservation.repository.MovieRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final SeatQueryService seatTicketService;

    public MovieService(MovieRepository movieRepository,
                        SeatQueryService seatTicketService) {
        this.movieRepository = movieRepository;
        this.seatTicketService = seatTicketService;
    }


    public Movie getMovieById(String id){
        return movieRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No movie with this ID was found."));
    }

    @Transactional
    public MovieResponse createMovie(MovieRequest movieRequest){
        Movie movie = new Movie(movieRequest);
        MovieValidateHandler.checkFieldsEmpty(movie);
        movie.setRooms(seatTicketService.createSeatsToMovie(movie));
        movieRepository.save(movie);
        return new MovieResponse(movie);
    }

    @Transactional
    public MovieResponse updateMovie(MovieRequestUpdate movieReqUpdate){

        Movie movieSavedDB = getMovieById(movieReqUpdate.id());
        Movie movieUpdated = new Movie(movieReqUpdate);
        movieUpdated.setRooms(movieSavedDB.getRooms());

        MovieValidateHandler.checkFieldsEmpty(movieUpdated);

        movieRepository.save(movieUpdated);
        return new MovieResponse(movieUpdated);
    }

    public List<MovieResponse> findAllMovies() {
        List<Movie> movieList = movieRepository.findAll();

        return movieList.stream()
                .map(MovieResponse::new)
                .toList();
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
