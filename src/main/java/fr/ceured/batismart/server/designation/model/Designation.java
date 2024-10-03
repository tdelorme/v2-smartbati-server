package fr.ceured.batismart.server.designation.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Designation {

    private String id;

    private String number;
    private String name;
    private Double priceExcludingTax;
    private Double priceIncludingTax;

}
