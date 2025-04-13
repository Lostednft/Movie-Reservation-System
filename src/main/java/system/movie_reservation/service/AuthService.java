package system.movie_reservation.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.movie_reservation.exception.UserValidationHandler;
import system.movie_reservation.model.user.User;
import system.movie_reservation.model.user.UserLogin;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.model.user.UserResponse;
import system.movie_reservation.repository.UserRepository;
import system.movie_reservation.security.TokenService;
import system.movie_reservation.service.usescases.AuthUsesCases;

@Service
public class AuthService implements AuthUsesCases {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       TokenService tokenService,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public String loginAuthenticate(UserLogin userLogin) {
        var authToken = new UsernamePasswordAuthenticationToken(
                userLogin.username(),
                userLogin.password()
        );
        Authentication authenticate = authenticationManager.authenticate(authToken);
        return tokenService.generate((User) authenticate.getPrincipal());
    }

    @Override
    public UserResponse registerUser(UserRequest user){
        User entity = new User(user);

        UserValidationHandler.checkEmptyFields(entity);
        UserValidationHandler.passwordValidate(entity.getPassword());
        UserValidationHandler.emailFormatValidate(entity.getEmail());
        UserValidationHandler.checkUsernameAndEmailAlreadyExist(
                userRepository.findUserByUsername(entity.getUsername()),
                userRepository.findUserByEmail(entity.getEmail()));

        String passwordEncoder = new BCryptPasswordEncoder().encode(user.password());
        entity.setPassword(passwordEncoder);

        userRepository.save(entity);
        return new UserResponse(entity);
    }
}