package fr.ceured.batismart.server.authentication.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class IdNotFoundException extends CustomException {
    public static final String ID_NOT_FOUND = "Id not found";

    public IdNotFoundException() {
        super(ID_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
