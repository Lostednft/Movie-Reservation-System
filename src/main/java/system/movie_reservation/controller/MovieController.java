package system.movie_reservation.controller;

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
    public ResponseEntity createMovie(@RequestBody MovieRequest movie){

        if(movie.categories().isEmpty()||
        movie.name() == null ||
        movie.duration() == null ||
        movie.posterUrl() == null ||
        movie.description() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algum campo esta nulo");

        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @PutMapping("/update")
    public ResponseEntity updateMovie(@RequestBody MovieRequest movieRequest){

        return ResponseEntity.status(HttpStatus.OK).body(movieService.updateMovie(movieRequest));
    }
}
