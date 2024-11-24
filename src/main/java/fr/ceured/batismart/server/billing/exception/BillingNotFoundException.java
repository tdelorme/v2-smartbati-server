package fr.ceured.batismart.server.billing.exception;

import fr.ceured.batismart.server.commons.CustomException;
import org.springframework.http.HttpStatus;

public class BillingNotFoundException extends CustomException {

    private static final String BILLING_NOT_FOUND = "Aucun devis ou aucune facture trouvé pour l'id %s";

    public BillingNotFoundException(String id) {
        super(String.format(BILLING_NOT_FOUND, id), HttpStatus.NOT_FOUND);
    }
}
