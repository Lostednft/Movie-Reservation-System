package system.movie_reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

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
    @WithMockUser(roles = "ADMIN")
    public void givenId_whenGetUserById_thenUserObject() throws Exception {
        //Given
        User userSaved = userRepository.save(user);

        //When
        ResultActions response = mockMvc.perform(get("/user/{id}", userSaved.getId()));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userSaved.getId()))
                .andExpect(jsonPath("$.username").value(userSaved.getUsername()))
                .andExpect(jsonPath("$.password").value(userSaved.getPassword()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void given_whenGetAllUsers_thenReturnUsersList() throws Exception {

        //Given
        List<User> usersSaved = userRepository.saveAll(List.of(
                user,
                User.builder()
                        .username("marcos")
                        .email("marcos@gmail.com")
                        .password("marcos123")
                        .dateOfBirth(LocalDate.of(2001, 5, 28))
                        .role(UserRole.ADMIN).build()
        ));

        userRepository.saveAll(usersSaved);

        //When
        ResultActions response = mockMvc.perform(get("/user/users"));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(usersSaved.getFirst().getId()))
                .andExpect(jsonPath("$[1].username").value(usersSaved.getLast().getUsername()))
                .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenUser_UpdateUser_thenReturnUserUpdated() throws Exception {
        //Given
        User userSaved = userRepository.save(user);

        //When
        ResultActions response = mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userSaved.getId()))
                .andExpect(jsonPath("$.username").value(userSaved.getUsername()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenId_UpdateDeleteUserById_thenReturnMessageString() throws Exception {

        //Given
        User userSaved = userRepository.save(user);

        //When
        ResultActions response = mockMvc.perform(delete("/user/{id}", userSaved.getId()));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }
}
