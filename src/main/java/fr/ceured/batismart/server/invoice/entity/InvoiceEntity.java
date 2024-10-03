package fr.ceured.batismart.server.invoice.entity;

import fr.ceured.batismart.server.client.entity.ClientEntity;
import fr.ceured.batismart.server.designation.entity.DesignationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("invoice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class InvoiceEntity {

    @Id
    private String id;

    private String number;
    private List<DesignationEntity> designations;
    private ClientEntity client;
    private Double totalExcludingTaxes;
    private Double totalIncludingTaxes;
    private String userId;
    private LocalDate date;
    private LocalDate dueDate;
}
