
package system.movie_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.movie_reservation.model.user.UserLogin;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.service.AuthService;
import system.movie_reservation.service.usescases.UserUsesCases;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserUsesCases userUsesCases;
    private final AuthService authService;

    public AuthenticationController(UserUsesCases userUsesCases,
                                    AuthService authService) {
        this.userUsesCases = userUsesCases;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserRequest user){
        return ResponseEntity.ok(userUsesCases.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserLogin user){
        return ResponseEntity.ok(authService.authenticate(user));
    }
}