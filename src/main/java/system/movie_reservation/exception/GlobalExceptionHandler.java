package system.movie_reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity HttpNullPointerException(NullPointerException nullPointerException){
        return new ResponseEntity(nullPointerException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity HttpNoSuchElementException(NoSuchElementException noSuchElementException){
        return new ResponseEntity(noSuchElementException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity httpIllegalArgumentException(IllegalArgumentException illegalArgumentException){
        return new ResponseEntity(illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
