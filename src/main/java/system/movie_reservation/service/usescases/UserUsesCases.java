package system.movie_reservation.service.usescases;

import system.movie_reservation.model.user.User;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.model.user.UserRequestUpdate;
import system.movie_reservation.model.user.UserResponse;

import java.util.List;

public interface UserUsesCases {

    User getUserById(String id);
    UserResponse createUser(UserRequest user);
    List<UserResponse> getAllUsers();
    UserResponse updateUserById(UserRequestUpdate userReqUpdate);
    String deleteUserById(String id);
    String removeAllUsers();
}