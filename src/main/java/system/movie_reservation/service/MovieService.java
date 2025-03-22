package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.exception.ValidateException;
import system.movie_reservation.model.Enum.MovieTime;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.Seat;
import system.movie_reservation.model.dto.MovieRequest;
import system.movie_reservation.model.dto.MovieResponse;
import system.movie_reservation.repository.MovieRepository;
import system.movie_reservation.repository.SeatRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final SeatRepository seatRepository;

    public MovieService(MovieRepository movieRepository,
                        SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.seatRepository = seatRepository;
    }

    public Movie getMovieById(String id){
        return movieRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No movie with this ID was found."));
    }

    @Transactional
    public MovieResponse createMovie(MovieRequest movieRequest){
        ValidateException.checkFieldsEmpty(movieRequest);
        Movie movie = new Movie(movieRequest);
        movie.setRooms(createAutoRooms(movie));
        movieRepository.save(movie);
        return new MovieResponse(movie);
    }

    public Movie updateMovie(MovieRequest movie){
        ValidateException.checkFieldsEmpty(movie);
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

    private List<Seat> createAutoRooms(Movie movie){
        List<Seat> seatList = List.of(
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime()),
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_02.toMovieTime()),
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_03.toMovieTime()));

        seatRepository.saveAll(seatList);
        return seatList;
    }
}
