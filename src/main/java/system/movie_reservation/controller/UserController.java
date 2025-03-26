package system.movie_reservation.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation.model.user.UserRequestUpdate;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.service.UserService;
import system.movie_reservation.service.usescases.UserUsesCases;

@RestController
@RequestMapping("auth")
public class UserController {

    private final UserUsesCases userUsesCases;

    public UserController(UserService userUsesCases) {
        this.userUsesCases = userUsesCases;
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody UserRequest user){
        return ResponseEntity.ok(userUsesCases.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id){
        return ResponseEntity.ok(userUsesCases.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userUsesCases.getAllUsers());
    }

    @PutMapping
    public ResponseEntity updateUserById(@RequestBody UserRequestUpdate user){
        return ResponseEntity.ok(userUsesCases.updateUserById(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable String id){
        return ResponseEntity.ok(userUsesCases.deleteUserById(id));
    }

    @DeleteMapping
    public ResponseEntity deleteAllUsers(){
        return ResponseEntity.ok(userUsesCases.removeAllUsers());
    }
}
