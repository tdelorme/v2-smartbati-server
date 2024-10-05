package fr.ceured.batismart.server.designation.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("invoice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class DesignationEntity {

    @Id
    private String id;

    private String number;
    private String name;
    private Double priceExcludingTax;
    private Double priceIncludingTax;
    private String userId;
    

}
