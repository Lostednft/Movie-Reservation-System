package system.movie_reservation.RepositoryLayerTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.repository.UserRepository;

import java.time.LocalDate;

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
    public void givenUsername_whenFindUserByUsername_thenUserObject(){

        //Given
        userRepository.save(user);

        //When
        User userByUsername = userRepository.findUserByUsername(user.getUsername());

        //Then
        Assertions.assertThat(userByUsername).isEqualTo(user);
        Assertions.assertThat(userByUsername.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void given_when_then(){

    }

    @Test
    public void given_when_then(){

    }
}
