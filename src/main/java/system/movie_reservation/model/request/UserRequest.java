package system.movie_reservation.model.request;

import system.movie_reservation.model.Enum.UserRole;
import java.time.LocalDate;

public record UserRequest(String username,
                          String password,
                          String email,
                          LocalDate dateOfBirth,
                          UserRole role) {
}
