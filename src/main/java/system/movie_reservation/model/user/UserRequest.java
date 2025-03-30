package system.movie_reservation.model.user;

import system.movie_reservation.model.user.Enum.UserRole;

import java.time.LocalDate;

public record UserRequest(String username,
                          String password,
                          String email,
                          LocalDate dateOfBirth) {
}
