package fr.ceured.batismart.server.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private String id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean active;
    private boolean accountVerified;

}
