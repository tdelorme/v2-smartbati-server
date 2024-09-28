package fr.ceured.batismart.server.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public abstract class CustomException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;



}
