package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.exception.MovieValidationHandler;
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
    private final MovieTheaterService movieTheaterService;
    private final SeatTicketService seatTicketService;

    public MovieService(MovieRepository movieRepository,
                        MovieTheaterService movieTheaterService,
                        SeatTicketService seatTicketService) {
        this.movieRepository = movieRepository;
        this.movieTheaterService = movieTheaterService;
        this.seatTicketService = seatTicketService;
    }


    public Movie getMovieById(String id){
        return movieRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No movie with this ID was found."));
    }

    @Transactional
    public MovieResponse createMovie(MovieRequest movieRequest){
        Movie movie = new Movie(movieRequest);
        MovieValidationHandler.checkFieldsEmpty(movie);
        movie.setRooms(movieTheaterService.createSeatsToMovie(movie));
        movieRepository.save(movie);
        return new MovieResponse(movie);
    }

    @Transactional
    public MovieResponse updateMovie(MovieRequestUpdate movieReqUpdate){
        Movie movieSavedDB = getMovieById(movieReqUpdate.id());
        Movie movieUpdated = new Movie(movieReqUpdate);
        MovieValidationHandler.checkFieldsEmpty(movieUpdated);
        movieUpdated.setRooms(movieSavedDB.getRooms());

        movieRepository.save(movieUpdated);
        return new MovieResponse(movieUpdated);
    }

    public List<MovieResponse> findAllMovies() {
        List<Movie> movieList = movieRepository.findAll();

        return movieList.stream()
                .map(MovieResponse::new)
                .toList();
    }

    public String removeMovieById(String id){
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
        return "Movie deleted successfully";
    }

    public String removeAllMovies(){
        if(movieRepository.findAll().isEmpty())
            return "No movies registered.";

        seatTicketService.deleteAllTicketsFromSeat();
        movieRepository.deleteAll();
        return "All movies was removed successfully";
    }
}
