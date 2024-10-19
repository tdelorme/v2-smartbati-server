package fr.ceured.batismart.server.designation.entity;

import fr.ceured.batismart.server.designation.model.enums.TypeDesignation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("designation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class DesignationEntity {

    @Id
    private String id;

    private String name;
    private Double price;
    private String userId;
    private TypeDesignation typeDesignation;

}
