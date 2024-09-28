package fr.ceured.batismart.server.authentication.exception;

import fr.ceured.batismart.server.commons.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserAlreadyExistException extends CustomException {

    private static final String USER_ALREADY_EXIST = "Un utilisateur existe déjà avec cet email";

    public UserAlreadyExistException() {
        super(USER_ALREADY_EXIST, HttpStatus.CONFLICT);
    }
}
