package system.movie_reservation.service.usescases;

import system.movie_reservation.model.user.UserLogin;
import system.movie_reservation.model.user.UserRequest;
import system.movie_reservation.model.user.UserResponse;

public interface AuthUsesCases {

    String loginAuthenticate(UserLogin userLogin);
    UserResponse registerUser(UserRequest user);

}
