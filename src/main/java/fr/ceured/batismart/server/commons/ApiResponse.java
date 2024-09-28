package fr.ceured.batismart.server.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ApiResponse<T> {

    private T data;
    private String errorMessage;
    private LocalDateTime dateTime;

}
