package fr.ceured.batismart.server.billing.model;

import fr.ceured.batismart.server.designation.model.Designation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LineQuantity {
    private String designationId;
    private Designation designation;
    private int quantity;
}
