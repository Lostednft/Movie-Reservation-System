
package system.movie_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.movie_reservation.model.user.UserLogin;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.service.usescases.AuthUsesCases;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUsesCases authUsesCases;

    public AuthController(AuthUsesCases authUsesCases) {
        this.authUsesCases = authUsesCases;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserRequest user){
        return ResponseEntity.ok(authUsesCases.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserLogin user){
        return ResponseEntity.ok(authUsesCases.loginAuthenticate(user));
    }
}