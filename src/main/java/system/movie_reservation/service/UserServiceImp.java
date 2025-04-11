package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.exception.UserValidationHandler;
import system.movie_reservation.model.user.*;
import system.movie_reservation.repository.UserRepository;
import system.movie_reservation.service.usescases.UserUsesCases;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImp implements UserUsesCases{

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(String id){
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No User founded with this ID."));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserRequestUpdate userReqUpdate) {
        User userById = getUserById(userReqUpdate.id());
        User userUpdated = new User(userReqUpdate);
        UserValidationHandler.checkEmptyFields(userUpdated);

        User userFoundByUsername = null;
        User userFoundByEmail = null;

        if(!userById.getUsername().equals(userUpdated.getUsername()))
            userFoundByUsername = getUserByUsername(userUpdated.getUsername());

        if(!userById.getEmail().equals(userUpdated.getEmail()))
            userFoundByEmail = userRepository.findUserByEmail(
                    userUpdated.getEmail());

        UserValidationHandler.checkUsernameAndEmailAlreadyExist(
                userFoundByUsername,
                userFoundByEmail);

        userRepository.save(userUpdated);
        return new UserResponse(userUpdated);
    }

    @Override
    public String deleteUserById(String id){
        getUserById(id);
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}
