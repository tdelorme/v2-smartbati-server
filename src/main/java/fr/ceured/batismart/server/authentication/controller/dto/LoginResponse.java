package fr.ceured.batismart.server.authentication.controller.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LoginResponse {

    private String username;
    private String jwt;
    private boolean authValid;
    private boolean tokenValid;
    private String message;
}
