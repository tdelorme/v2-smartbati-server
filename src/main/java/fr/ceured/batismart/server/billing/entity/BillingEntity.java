package fr.ceured.batismart.server.billing.entity;

import fr.ceured.batismart.server.billing.model.LineQuantity;
import fr.ceured.batismart.server.billing.model.enums.BillingType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("billing")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillingEntity {

    @Id
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
