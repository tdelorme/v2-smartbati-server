package fr.ceured.batismart.server.billing.model;

import fr.ceured.batismart.server.billing.model.enums.BillingType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Billing {

    private String id;

    private String number;
    private List<LineQuantity> designationIds;
    private String clientId;
    private Double totalExcludingTaxes;
    private Double totalIncludingTaxes;
    private String userId;
    private LocalDate date;
    private LocalDate dueDate;
    private BillingType type;
    private String generatedFile;
    private Double discountPercent;
}
