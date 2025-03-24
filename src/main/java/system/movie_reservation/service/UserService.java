package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.exception.UserValidateHandler;
import system.movie_reservation.model.User;
import system.movie_reservation.model.request.ToUpdate.UserRequestUpdate;
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

        User entity = new User(user);
        UserValidateHandler.checkFieldsEmpty(entity);
        return userRepository.save(entity);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserById(UserRequestUpdate userReqUpdate) {
        getUserById(userReqUpdate.id());

        User user = new User(userReqUpdate);
        UserValidateHandler.checkFieldsEmpty(user);

        return user;
    }

    public String deleteUserById(String id){
        getUserById(id);
        userRepository.deleteById(id);

        return "Movie deleted successfully";
    }

    public String removeAllUsers() {

        if(userRepository.findAll().isEmpty())
            return "No movie registered.";

        userRepository.deleteAll();
        return "All movies was removed successfully";
    }
}
