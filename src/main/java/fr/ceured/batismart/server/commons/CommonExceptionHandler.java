package fr.ceured.batismart.server.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException ex) {
        log.error(ex.getMessage(), ex);
        ApiResponse<Object> body = ApiResponse.builder()
                .errorMessage(ex.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }

}
