package fr.ceured.batismart.server.client.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends CustomException {

    private static final String CLIENT_NOT_FOUND = "Client not found with id %s";

    public ClientNotFoundException(String id) {
        super(String.format(CLIENT_NOT_FOUND, id), HttpStatus.NOT_FOUND);
    }
}
