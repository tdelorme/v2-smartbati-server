package fr.ceured.batismart.server.designation.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class DesignationNotFoundException extends CustomException {

    private static final String message = "Designation not found with id %s";

    public DesignationNotFoundException(String id) {
        super(String.format(message, id), HttpStatus.NOT_FOUND);
    }
}
