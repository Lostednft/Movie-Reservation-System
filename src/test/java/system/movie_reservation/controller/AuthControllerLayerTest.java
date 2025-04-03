package system.movie_reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.model.user.UserLogin;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.repository.UserRepository;

import java.time.LocalDate;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerLayerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setup(){
        userRepository.deleteAll();

        user = User.builder()
                .username("renata")
                .email("renata@gmail.com")
                .dateOfBirth(LocalDate.of(1998, 3, 11))
                .password("senha12345")
                .role(UserRole.ADMIN)
                .build();
    }

    @Test
    public void givenUserRequest_whenRegisterUser_thenUserResponse() throws Exception{

        //Given
        UserRequest userRequest = new UserRequest(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getDateOfBirth());

        //When
        ResultActions response = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));

    }

    @Test
    public void givenUserLogin_whenLoginAuthenticate_thenTokenString() throws Exception {

        //Given
        UserLogin userLogin = new UserLogin(user.getUsername(), user.getPassword());

        PasswordEncoder passwordEncoded = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoded.encode(user.getPassword()));
        userRepository.save(user);

        //When
        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin)));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyString())));
    }
}
