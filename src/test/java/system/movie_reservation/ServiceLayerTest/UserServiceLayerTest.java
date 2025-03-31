package system.movie_reservation.ServiceLayerTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.model.user.UserRequestUpdate;
import system.movie_reservation.model.user.UserResponse;
import system.movie_reservation.repository.UserRepository;
import system.movie_reservation.service.UserServiceImp;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserServiceLayerTest {

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Mock
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setup(){

        user = User.builder()
                .username("vinicius")
                .password("123")
                .role(UserRole.ADMIN)
                .email("vinicius@gmail.com")
                .dateOfBirth(LocalDate.of(1998, 7, 15))
                .build();
    }

    @Test
    public void givenId_whenGetUserById_thenReturnUserObject(){

        //given
        BDDMockito.given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        //when
        User userById = userServiceImp.getUserById(user.getId());

        //then
        Assertions.assertThat(userById).isEqualTo(user);
        Assertions.assertThat(userById.getId()).isEqualTo(user.getId());
    }


    @Test
    public void givenUsername_whenGetUserByUsername_thenReturnUserObject(){

        //given
        BDDMockito.given(userRepository.findUserByUsername(user.getUsername())).willReturn(user);

        //when
        User userById = userServiceImp.getUserByUsername(user.getUsername());

        //then
        Assertions.assertThat(userById).isEqualTo(user);
        Assertions.assertThat(userById.getUsername()).isEqualTo(user.getUsername());
    }


    @Test
    public void given_whenGetAllUsers_thenReturnListUsers(){


        userRepository.save(user);

        //given
        BDDMockito.given(userRepository.findAll()).willReturn(List.of(user));

        //when
        List<UserResponse> allUsers = userServiceImp.getAllUsers();
        var userResponse = allUsers.getLast();

        //then
        Assertions.assertThat(allUsers).isNotEmpty();
        Assertions.assertThat(userResponse.id()).isEqualTo(user.getId());
    }

    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUserObject(){

        UserRequestUpdate userRequest = new UserRequestUpdate(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                LocalDate.of(1989,12,4),
                user.getRole());

        //given
        BDDMockito.given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        BDDMockito.given(userRepository.save(BDDMockito.any(User.class))).willReturn(user);

        //when
        UserResponse userResponse = userServiceImp.updateUser(userRequest);

        //then
        Assertions.assertThat(userResponse.username()).isEqualTo(userRequest.username());
        Assertions.assertThat(userResponse.id()).isEqualTo(user.getId());
        Assertions.assertThat(userResponse.email()).isEqualTo(userRequest.email());
    }

    @Test
    public void givenId_whenDeleteById_thenReturnMessage(){

        //given
        userRepository.save(user);
        BDDMockito.given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        BDDMockito.willDoNothing().given(userRepository).deleteById(user.getId());

        //when
        List<User> allUsers = userRepository.findAll();
        String message = userServiceImp.deleteUserById(user.getId());

        //then
        Assertions.assertThat(allUsers).doesNotContain(user);
        Assertions.assertThat(message).isEqualTo("User deleted successfully");
    }

}
