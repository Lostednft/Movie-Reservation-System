package system.movie_reservation.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.movie_reservation.exception.UserValidationHandler;
import system.movie_reservation.model.user.*;
import system.movie_reservation.repository.UserRepository;
import system.movie_reservation.security.TokenService;
import system.movie_reservation.service.usescases.UserUsesCases;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImp implements UserUsesCases{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserServiceImp(UserRepository userRepository,
                          @Lazy AuthenticationManager authenticationManager,
                          TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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
    public UserResponse registerUser(UserRequest user){
        User entity = new User(user);
        UserValidationHandler.checkEmptyFields(entity);
        UserValidationHandler.checkUsernameAndEmailAlreadyExist(getUserByUsername(entity.getUsername()), userRepository.findUserByEmail(entity.getEmail()));
        String passwordEncoder = new BCryptPasswordEncoder().encode(user.password());
        entity.setPassword(passwordEncoder);

        userRepository.save(entity);
        return new UserResponse(entity);
    }

    @Override
    public String loginUserValidation(UserLogin user) {

        loginPasswordException(user);
        var tokenUserPassword = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        Authentication authenticate = authenticationManager.authenticate(tokenUserPassword);
        String generate = tokenService.generate((User) authenticate.getPrincipal());
        return generate;
    }

    private void loginPasswordException(UserLogin data){
        if(data.username().isEmpty() || data.password().isEmpty())
            throw new NullPointerException("All fields must be filled in.");

        if (getUserByUsername(data.username()) == null)
            throw new UsernameNotFoundException("login invalid.");

    }


    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse updateUserById(UserRequestUpdate userReqUpdate) {
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

    @Override
    public String removeAllUsers() {
        if(userRepository.findAll().isEmpty())
            return "No users registered.";

        userRepository.deleteAll();
        return "All users was removed successfully";
    }

}
