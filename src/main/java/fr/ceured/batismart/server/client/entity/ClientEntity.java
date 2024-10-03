package fr.ceured.batismart.server.client.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("client")
public class ClientEntity {

    @Id
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String address;
    private String userId;

}
