package system.movie_reservation.service.usescases;

import system.movie_reservation.model.user.*;

import java.util.List;

public interface UserUsesCases {

    User getUserById(String id);
    User getUserByUsername(String username);
    List<UserResponse> getAllUsers();
    UserResponse updateUserById(UserRequestUpdate userReqUpdate);
    String deleteUserById(String id);
}