package fr.ceured.batismart.server.invoice.mapper;

import fr.ceured.batismart.server.invoice.entity.InvoiceEntity;
import fr.ceured.batismart.server.invoice.model.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceEntity invoiceToInvoiceEntity(Invoice invoice);
    Invoice invoiceEntityToInvoice(InvoiceEntity invoice);
}
