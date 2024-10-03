package fr.ceured.batismart.server.authentication.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends CustomException {

    public static final String EMAIL_NOT_FOUND = "Email not found";

    public EmailNotFoundException() {
        super(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
