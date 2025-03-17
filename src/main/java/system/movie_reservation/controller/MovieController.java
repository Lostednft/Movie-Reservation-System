package system.movie_reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation.model.dto.MovieRequest;
import system.movie_reservation.service.MovieService;

@RestController
@RequestMapping("movies")
public class MovieController {

    public final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/create")
    public ResponseEntity createMovie(@Valid @RequestBody MovieRequest movie){
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @PutMapping("/update")
    public ResponseEntity updateMovie(@RequestBody MovieRequest movieRequest){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.updateMovie(movieRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity getMovieById(@PathVariable String id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity getAlLMovies(){
        if(movieService.findAllMovies().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No movie found.");
        return ResponseEntity.ok(movieService.findAllMovies());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovieById(@PathVariable String id){
        if(id.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID field is empty.");
        movieService.removeMovieById(id);
        return ResponseEntity.ok("Movie deleted successfully.");
    }

    @DeleteMapping
    public ResponseEntity deleteAllMovies(){
        movieService.removeAllMovies();
        return ResponseEntity.ok("All movies was removed successfully");
    }
}
