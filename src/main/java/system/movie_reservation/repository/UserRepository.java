package system.movie_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.movie_reservation.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
