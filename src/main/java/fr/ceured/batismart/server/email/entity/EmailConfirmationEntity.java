package fr.ceured.batismart.server.email.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "emailConfirmation")
public class EmailConfirmationEntity {

    @Id
    private String id;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime verificationDate;
}
