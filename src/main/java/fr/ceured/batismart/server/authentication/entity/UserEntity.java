package fr.ceured.batismart.server.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean active;
    private boolean accountVerified;

}
