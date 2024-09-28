package fr.ceured.batismart.server.authentication.exception;


import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {

    private static final String INVALID_TOKEN = "The token is invalid";

    private InvalidTokenException() {
        super(INVALID_TOKEN, HttpStatus.FORBIDDEN);
    }

}
