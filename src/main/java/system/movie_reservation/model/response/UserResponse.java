package system.movie_reservation.model.response;

import system.movie_reservation.model.Enum.UserRole;
import system.movie_reservation.model.User;

import java.time.LocalDate;

public record UserResponse(String id,
                           String username,
                           String password,
                           String email,
                           LocalDate dateOfBirth,
                           UserRole role) {

    public UserResponse(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getRole()
        );
    }
}

