package system.movie_reservation.exception;

import system.movie_reservation.model.user.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidationHandler {

    public static void checkEmptyFields(User user){
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

    public static void checkUsernameAndEmailAlreadyExist(User userUsername, User userEmail){

        String response = "";

        if(userUsername != null)
            response += "That username already exist.";

        if(userEmail != null)
            response += "That email already exist.";

        if (!response.isEmpty())
            throw new IllegalArgumentException(response.replace(".", "\n"));
    }

    public static void emailFormatValidate(String email){

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.find())
            throw new IllegalArgumentException("This email is in an invalid format, default example: movie@gmail.com");

    }

    public static void passwordValidate(String password){
        String response = "";

        if(password.length() < 8)
            response = "The password must have at least 8 characters.";

        if(!Pattern.compile("[A-Z]").matcher(password).find())
            response += "The password must contain at least one capital letter.";

        if(!Pattern.compile("[a-z]").matcher(password).find())
            response += "The password must contain at least one lowercase letter.";

        if(!Pattern.compile("[0-9]").matcher(password).find())
            response += "\"The password must have at least one number.";

        if(!Pattern.compile("\\W").matcher(password).find())
            response += "The password must have at least one special character.";

        if (!response.isEmpty())
            throw new IllegalArgumentException(response.replace(".", "\n"));
    }
}