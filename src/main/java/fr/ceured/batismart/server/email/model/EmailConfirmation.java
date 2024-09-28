package fr.ceured.batismart.server.email.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfirmation {

    private String id;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime verificationDate;
}
