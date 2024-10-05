package fr.ceured.batismart.server.billing.mapper;

import fr.ceured.batismart.server.billing.entity.BillingEntity;
import fr.ceured.batismart.server.billing.model.Billing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillingMapper {
    BillingEntity invoiceToInvoiceEntity(Billing billing);
    Billing invoiceEntityToInvoice(BillingEntity invoice);
}
