package system.movie_reservation.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import system.movie_reservation.model.user.User;
import system.movie_reservation.model.user.UserLogin;
import system.movie_reservation.security.TokenService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(AuthenticationManager authenticationManager,
                       TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String authenticate(UserLogin userLogin) {
        var authToken = new UsernamePasswordAuthenticationToken(
                userLogin.username(),
                userLogin.password()
        );
        Authentication authenticate = authenticationManager.authenticate(authToken);
        return tokenService.generate((User) authenticate.getPrincipal());
    }
}