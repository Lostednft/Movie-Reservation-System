package system.movie_reservation.model.dto;

import system.movie_reservation.model.Enum.UserRole;
import java.time.LocalDate;

public record UserDto(String username,
                      String password,
                      String email,
                      LocalDate dateOfBirth,
                      UserRole role) {
}
