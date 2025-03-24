package system.movie_reservation.exception;

import system.movie_reservation.model.User;

public class UserValidateHandler {

    public static void checkFieldsEmpty(User user){
        String response = "";

        if(user.getUsername().isEmpty())
            response = "the field username is required.";
        if(user.getPassword().isEmpty())
            response += "the field password is required.";
        if(user.getEmail().isEmpty())
            response += "the field email is required.";
        if(user.getRole() == null)
            response += "the field role is required.";
        if(user.getDateOfBirth() == null)
            response += "the field dateOfBirth is required.";

        if (!response.isEmpty())
            throw new IllegalArgumentException(response.replace(".", "\n"));
    }
}
