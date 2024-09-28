package fr.ceured.batismart.server.commons;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException ex) {
        ApiResponse<Object> body = ApiResponse.builder()
                .errorMessage(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }

}
