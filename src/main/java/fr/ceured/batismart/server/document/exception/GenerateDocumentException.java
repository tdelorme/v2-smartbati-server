package fr.ceured.batismart.server.document.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class GenerateDocumentException extends CustomException {
    public GenerateDocumentException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
