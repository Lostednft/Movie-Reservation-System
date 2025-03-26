package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.repository.MovieTheaterRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieTheaterService {

    private final MovieTheaterRepository movieTheaterRepository;

    public MovieTheaterService(MovieTheaterRepository movieTheaterRepository) {
        this.movieTheaterRepository = movieTheaterRepository;

    }

    MovieTheater getSeatById(Long id){
        return movieTheaterRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No Seat found with this ID."));
    }

    MovieTheater getSeatByMovieAndMovieTime(Movie movie, MovieTime movieTime){
        return movieTheaterRepository.findByMovieAndMovieTime(movie, movieTime).orElseThrow(() ->
                new NoSuchElementException("No Seat found with this movie and movieTime."));
    }

    List<MovieTheater> createSeatsToMovie(Movie movie){
        List<MovieTheater> movieTheaterList = List.of(
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_02.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_03.toMovieTime()));

        movieTheaterRepository.saveAll(movieTheaterList);
        return movieTheaterList;
    }
}
