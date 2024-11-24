package fr.ceured.batismart.server.billing.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class ExceedTotalException extends CustomException {

    private static final String EXCEED_TOTAL_EXCEPTION = "Le montant total est excédé en comptant l'accompte !";

    public ExceedTotalException() {
        super(EXCEED_TOTAL_EXCEPTION, HttpStatus.BAD_REQUEST);
    }
}
