package system.movie_reservation.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .dateOfBirth(LocalDate.of(1998, 7, 15))
                .email("adriana@gmail.com")
                .role(UserRole.ADMIN)
                .password("1234")
                .username("adriana")
                .build();
    }

    @Test
    @DisplayName("JUnit for get User by Email")
    public void givenEmail_whenFindUserByEmail_thenReturnUserObject(){

        //Given
        userRepository.save(user);

        //When
        User userByEmail = userRepository.findUserByEmail(user.getEmail());

        //Then
        Assertions.assertThat(userByEmail).isEqualTo(user);
        Assertions.assertThat(userByEmail.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void givenUsername_whenFindUserByUsername_thenReturnUserObject(){

        //Given
        userRepository.save(user);

        //When
        User userByUsername = userRepository.findUserByUsername(user.getUsername());

        //Then
        Assertions.assertThat(userByUsername).isEqualTo(user);
        Assertions.assertThat(userByUsername.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void givenId_whenFindUserById_thenReturnUserObject(){

        //Given
        userRepository.save(user);

        //When
        User userById = userRepository.findById(user.getId()).get();

        //Then
        Assertions.assertThat(userById).isEqualTo(user);
        Assertions.assertThat(userById.getId()).isEqualTo(user.getId());

    }

    @Test
    public void given_whenFindAll_thenReturnAllUsersObjects(){

        //Given
        userRepository.save(user);

        //When
        List<User> allUsers = userRepository.findAll();

        //Then
        Assertions.assertThat(allUsers).isNotEmpty();
        Assertions.assertThat(allUsers).contains(user);
        Assertions.assertThat(allUsers.getLast().getId()).isEqualTo(user.getId());
    }


    @Test
    public void givenId_whenDeleteById_thenReturnUsersList(){

        //Given
        userRepository.save(user);

        //When
        userRepository.deleteById(user.getId());
        List<User> allUsers = userRepository.findAll();

        //Then
        Assertions.assertThat(allUsers).doesNotContain(user);

    }
}
