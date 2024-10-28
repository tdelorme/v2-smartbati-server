package fr.ceured.batismart.server.billing.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BillingType {

    QUOTE("DEVIS"),
    INVOICE("FACTURE"),
    INVOICE_PAID("FACTURE_PAYE");

    private final String value;

}
