package system.movie_reservation.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import system.movie_reservation.model.user.Enum.UserRole;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role.equals(UserRole.ADMIN))
           return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
