package fr.ceured.batismart.server.authentication.exception;

import org.springframework.http.HttpStatus;

import fr.ceured.batismart.server.commons.CustomException;

public class NoVerifyMailFoundException extends CustomException {
    
    private static final String NO_VERIFY_MAIL_FOUND = "Aucun email de confirmation n'a été envoyé pour l'email %s";

    public NoVerifyMailFoundException(final String email) {
        super(String.format(NO_VERIFY_MAIL_FOUND, email), HttpStatus.NOT_FOUND);
    }

}
