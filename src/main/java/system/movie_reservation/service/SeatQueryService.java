package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.Seat;
import system.movie_reservation.repository.SeatRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SeatQueryService {

    private final SeatRepository seatRepository;

    public SeatQueryService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;

    }

    Seat getSeatById(Long id){
        return seatRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No Seat found with this ID."));
    }

    Seat getSeatByMovieAndMovieTime(Movie movie, MovieTime movieTime){
        return seatRepository.findByMovieAndMovieTime(movie, movieTime).orElseThrow(() ->
                new NoSuchElementException("No Seat found with this movie and movieTime."));
    }

    List<Seat> createSeatsToMovie(Movie movie){
        List<Seat> seatList = List.of(
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime()),
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_02.toMovieTime()),
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_03.toMovieTime()));

        seatRepository.saveAll(seatList);
        return seatList;
    }
}
