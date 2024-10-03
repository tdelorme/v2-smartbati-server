package fr.ceured.batismart.server.commons;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class ApiResponse<T> {

    private T data;
    private String errorMessage;
    private LocalDateTime dateTime;

}
