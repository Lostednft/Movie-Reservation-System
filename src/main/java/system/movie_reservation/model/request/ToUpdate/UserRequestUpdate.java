package system.movie_reservation.model.request.ToUpdate;

import system.movie_reservation.model.Enum.UserRole;

import java.time.LocalDate;

public record UserRequestUpdate(String id,
                                String username,
                                String password,
                                String email,
                                LocalDate dateOfBirth,
                                UserRole role) {
}
