package fr.ceured.batismart.server.billing.service;

import fr.ceured.batismart.server.billing.mapper.BillingMapper;
import fr.ceured.batismart.server.billing.model.Billing;
import fr.ceured.batismart.server.billing.repository.BillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final BillingMapper billingMapper;

    public List<Billing> findAllBilling() {
        return this.billingRepository.findAll()
                .stream()
                .map(billingMapper::invoiceEntityToInvoice)
                .toList();
    }


}
