package fr.ceured.batismart.server.invoice.service;

import fr.ceured.batismart.server.invoice.mapper.InvoiceMapper;
import fr.ceured.batismart.server.invoice.model.Invoice;
import fr.ceured.batismart.server.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public List<Invoice> findAllInvoices() {
        return this.invoiceRepository.findAll().stream().map(invoiceMapper::invoiceEntityToInvoice).toList();
    }


}
