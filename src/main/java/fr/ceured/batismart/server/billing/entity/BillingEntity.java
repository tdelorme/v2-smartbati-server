package fr.ceured.batismart.server.billing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("billing")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BillingEntity {

    @Id
    private String id;

    private String number;
    private List<String> designationIds;
    private String clientId;
    private Double totalExcludingTaxes;
    private Double totalIncludingTaxes;
    private String userId;
    private LocalDate date;
    private LocalDate dueDate;
}
