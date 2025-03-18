package system.movie_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.Enums.UserRole;
import system.movie_reservation.model.dto.UserDto;

import java.time.LocalDate;

@Entity
@Table(name = "user_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private String email;
    private LocalDate dateOfBirth;
    private UserRole role;

    public User(UserDto userDto) {
        this.username = userDto.username();
        this.password = userDto.password();
        this.email = userDto.email();
        this.dateOfBirth = userDto.dateOfBirth();
        this.role = userDto.role();
    }
}
