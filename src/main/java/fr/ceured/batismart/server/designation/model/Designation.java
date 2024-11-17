package fr.ceured.batismart.server.designation.model;

import fr.ceured.batismart.server.designation.model.enums.TypeDesignation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Designation {

    private String id;

    private String name;
    private Double price;
    private String description;
    private TypeDesignation typeDesignation;

}
