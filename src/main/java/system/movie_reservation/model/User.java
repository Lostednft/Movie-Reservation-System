package system.movie_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.Enum.UserRole;
import system.movie_reservation.model.request.ToUpdate.UserRequestUpdate;
import system.movie_reservation.model.request.UserRequest;

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
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private LocalDate dateOfBirth;
    private UserRole role;

    public User(UserRequest userRequest) {
        this.username = userRequest.username();
        this.password = userRequest.password();
        this.email = userRequest.email();
        this.dateOfBirth = userRequest.dateOfBirth();
        this.role = userRequest.role();
    }

    public User(UserRequestUpdate userReqUpdate) {
        this.id = userReqUpdate.id();
        this.username = userReqUpdate.username();
        this.password = userReqUpdate.password();
        this.email = userReqUpdate.email();
        this.dateOfBirth = userReqUpdate.dateOfBirth();
        this.role = userReqUpdate.role();
    }
}
