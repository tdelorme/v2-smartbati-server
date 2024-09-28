package fr.ceured.batismart.server.authentication.exception;

import fr.ceured.batismart.server.commons.CustomException;

import org.springframework.http.HttpStatus;

public class UserAlreadyVerifiedException extends CustomException {

    private static final String USER_ALREADY_VERIFIED = "User already verified";

    public UserAlreadyVerifiedException() {
        super(USER_ALREADY_VERIFIED, HttpStatus.ALREADY_REPORTED);
    }
}
