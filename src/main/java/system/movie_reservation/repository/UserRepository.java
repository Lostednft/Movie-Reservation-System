package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.User.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findUserByUsername (String username);

    User findUserByEmail(String email);
}
