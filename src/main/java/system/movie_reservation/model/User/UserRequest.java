package system.movie_reservation.model.User;

import system.movie_reservation.model.User.Enum.UserRole;
import java.time.LocalDate;

public record UserRequest(String username,
                          String password,
                          String email,
                          LocalDate dateOfBirth,
                          UserRole role) {
}
