package system.movie_reservation.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation.model.user.UserRequestUpdate;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.service.UserService;

@RestController
@RequestMapping("auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody UserRequest user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity updateUserById(@RequestBody UserRequestUpdate user){
        return ResponseEntity.ok(userService.updateUserById(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable String id){

        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @DeleteMapping
    public ResponseEntity deleteAllUsers(){

        return ResponseEntity.ok(userService.removeAllUsers());
    }
}
