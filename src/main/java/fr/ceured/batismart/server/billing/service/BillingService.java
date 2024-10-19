package fr.ceured.batismart.server.billing.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.billing.mapper.BillingMapper;
import fr.ceured.batismart.server.billing.model.Billing;
import fr.ceured.batismart.server.billing.repository.BillingRepository;
import fr.ceured.batismart.server.client.service.ClientService;
import fr.ceured.batismart.server.designation.service.DesignationService;
import fr.ceured.batismart.server.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final BillingMapper billingMapper;
    private final UserService userService;
    private final DesignationService designationService;
    private final DocumentService documentService;
    private final ClientService clientService;

    public List<Billing> findAllBilling() {

        User user = userService.getUserInSecurityConfig();

        return this.billingRepository.findAllByUserId(user.getId()).stream().map(billingMapper::invoiceEntityToInvoice).toList();
    }

    public Billing createBilling(Billing billing) {
        User user = userService.getUserInSecurityConfig();

        billing.setUserId(user.getId());
        billing.setGeneratedFile(documentService.generateDocumentFromBilling(billing));

        return billingMapper.invoiceEntityToInvoice(billingRepository.save(billingMapper.invoiceToInvoiceEntity(billing)));
    }

}
