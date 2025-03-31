package system.movie_reservation.ServiceLayerTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.model.user.UserLogin;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.model.user.UserResponse;
import system.movie_reservation.repository.UserRepository;
import system.movie_reservation.security.TokenService;
import system.movie_reservation.service.AuthService;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class AuthServiceLayerTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private TokenService tokenService;


    private User user;
    private String jwtToken;


    @BeforeEach
    public void setup(){

        user = User.builder()
                .username("patata")
                .password("1234")
                .role(UserRole.ADMIN)
                .email("patata@gmail.com")
                .dateOfBirth(LocalDate.of(1998, 7, 15))
                .build();
    }

    @Test
    public void givenUserRequest_whenRegisterUser_thenReturnUserResponseObject(){

        UserRequest userRequest = new UserRequest(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getDateOfBirth());

        //given
        BDDMockito.given(userRepository.findUserByEmail(user.getEmail())).willReturn(null);
        BDDMockito.given(userRepository.findUserByUsername(user.getUsername())).willReturn(null);
        BDDMockito.given(userRepository.save(BDDMockito.any(User.class))).willReturn(user);

        //when
        UserResponse userResponse = authService.registerUser(userRequest);


        //then
        Assertions.assertThat(userResponse.email()).isEqualTo(userRequest.email());
        Assertions.assertThat(userResponse.username()).isEqualTo(userRequest.username());
        Assertions.assertThat(userResponse.id()).isEqualTo(user.getId());

    }

    @Test
    public void givenUserLogin_whenLoginAuthenticate_thenReturnToken(){

        jwtToken = "anyTokenResponse";
        UserLogin userLogin = new UserLogin(user.getUsername(), user.getPassword());
        var authToken = new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password());

        //Given
        BDDMockito.given(authenticationManager.authenticate(authToken)).willReturn(authentication);
        BDDMockito.given(authentication.getPrincipal()).willReturn(user);
        BDDMockito.given(tokenService.generate(user)).willReturn("anyTokenResponse");

        //When
        String token = authService.loginAuthenticate(userLogin);


        //Then
        Assertions.assertThat(token).isNotEmpty();
        Assertions.assertThat(token).isEqualTo("anyTokenResponse");
    }
}
