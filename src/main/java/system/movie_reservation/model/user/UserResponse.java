package system.movie_reservation.model.user;

import system.movie_reservation.model.user.Enum.UserRole;

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

