package fr.ceured.batismart.server.billing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Billing {

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
