package system.movie_reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation.model.movie.MovieRequest;
import system.movie_reservation.model.movie.MovieRequestUpdate;
import system.movie_reservation.service.usescases.MovieUsesCases;

@RestController
@RequestMapping("movies")
public class MovieController {

    public final MovieUsesCases movieUsesCases;

    public MovieController(MovieUsesCases movieUsesCases) {
        this.movieUsesCases = movieUsesCases;
    }

    @PutMapping("/update")
    public ResponseEntity updateMovie(@RequestBody MovieRequestUpdate movieRequestUpdate){
        return ResponseEntity.status(HttpStatus.OK).body(movieUsesCases.updateMovie(movieRequestUpdate));
    }

    @GetMapping("/{id}")
    public ResponseEntity getMovieById(@PathVariable String id){
        return ResponseEntity.ok(movieUsesCases.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity getAlLMovies(){
        if(movieUsesCases.findAllMovies().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body("No movie registered.");
        return ResponseEntity.ok(movieUsesCases.findAllMovies());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovieById(@PathVariable String id){
        movieUsesCases.removeMovieById(id);
        return ResponseEntity.ok().body("Movie deleted successfully.");
    }

    @DeleteMapping
    public ResponseEntity deleteAllMovies(){
        movieUsesCases.removeAllMovies();
        return ResponseEntity.ok("All movies was removed successfully");
    }
}
