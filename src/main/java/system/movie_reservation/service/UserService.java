package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.exception.UserValidateHandler;
import system.movie_reservation.model.User.User;
import system.movie_reservation.model.User.UserRequestUpdate;
import system.movie_reservation.model.User.UserRequest;
import system.movie_reservation.model.User.UserResponse;
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

    public UserResponse createUser(UserRequest user){

        User entity = new User(user);
        UserValidateHandler.checkFieldsEmpty(entity);

        UserValidateHandler.checkUsernameAndEmailAlreadyExist(
                userRepository.findUserByUsername(entity.getUsername()),
                userRepository.findUserByEmail(entity.getEmail())
        );
        userRepository.save(entity);

        return new UserResponse(entity);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .toList();
    }

    public UserResponse updateUserById(UserRequestUpdate userReqUpdate) {

        User userById = getUserById(userReqUpdate.id());
        User userUpdated = new User(userReqUpdate);

        UserValidateHandler.checkFieldsEmpty(userUpdated);

        User userFoundByUsername = null;
        User userFoundByEmail = null;

        if(!userById.getUsername().equals(userUpdated.getUsername()))
            userFoundByUsername = userRepository.findUserByUsername(
                    userUpdated.getUsername());

        if(!userById.getEmail().equals(userUpdated.getEmail()))
            userFoundByEmail = userRepository.findUserByEmail(
                    userUpdated.getEmail());

        UserValidateHandler.checkUsernameAndEmailAlreadyExist(
                userFoundByUsername,
                userFoundByEmail);

        return new UserResponse(userUpdated);
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
