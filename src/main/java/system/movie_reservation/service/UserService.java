package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.User;
import system.movie_reservation.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(User user){
        return userRepository.save(user);
    }
}
