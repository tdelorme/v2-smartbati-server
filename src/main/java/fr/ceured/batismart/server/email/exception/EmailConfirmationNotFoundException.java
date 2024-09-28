package fr.ceured.batismart.server.email.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class EmailConfirmationNotFoundException extends CustomException {

    private static final String EMAIL_CONFIRMATION_NOT_FOUND = "Aucun email trouvé pour la confirmation";

    public EmailConfirmationNotFoundException() {
      super(EMAIL_CONFIRMATION_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
