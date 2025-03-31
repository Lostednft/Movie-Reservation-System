package system.movie_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.movie_reservation.model.user.UserRequestUpdate;
import system.movie_reservation.service.UserServiceImp;
import system.movie_reservation.service.usescases.UserUsesCases;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserUsesCases userUsesCases;

    public UserController(UserServiceImp userUsesCases) {
        this.userUsesCases = userUsesCases;
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
        return ResponseEntity.ok(userUsesCases.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable String id){
        return ResponseEntity.ok(userUsesCases.deleteUserById(id));
    }
}
