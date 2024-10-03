package fr.ceured.batismart.server.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Client {
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String address;
}
