package fr.ceured.batismart.server.authentication.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidInputException extends CustomException {

    private static final String INVALID_INPUT = "Les champs requis ne sont pas présent";

    public InvalidInputException() {
        super(INVALID_INPUT, HttpStatus.BAD_REQUEST);
    }
}
