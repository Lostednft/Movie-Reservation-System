package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.User;
import system.movie_reservation.model.request.UserRequest;
import system.movie_reservation.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String id){
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No User founded with this ID."));
    }

    public User createUser(UserRequest user){
        return userRepository.save(new User(user));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
