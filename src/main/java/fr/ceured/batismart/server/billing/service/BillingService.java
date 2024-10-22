package fr.ceured.batismart.server.billing.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.billing.mapper.BillingMapper;
import fr.ceured.batismart.server.billing.model.Billing;
import fr.ceured.batismart.server.billing.model.enums.BillingType;
import fr.ceured.batismart.server.billing.repository.BillingRepository;
import fr.ceured.batismart.server.client.service.ClientService;
import fr.ceured.batismart.server.counter.service.CounterService;
import fr.ceured.batismart.server.designation.model.enums.TypeDesignation;
import fr.ceured.batismart.server.designation.service.DesignationService;
import fr.ceured.batismart.server.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final BillingMapper billingMapper;
    private final UserService userService;
    private final DesignationService designationService;
    private final DocumentService documentService;
    private final CounterService counterService;
    private final ClientService clientService;

    public Page<Billing> getAllBillingByType(BillingType billingType, Pageable pageable) {
        User user = userService.getUserInSecurityConfig();

        return this.billingRepository.findAllByTypeAndUserId(billingType, user.getId(), pageable)
                .map(billingMapper::invoiceEntityToInvoice)
                .map(billing -> {
                    billing.setClient(clientService.getClientById(billing.getClientId()));
                    return billing;
                });
    }

    public Billing createBilling(Billing billing) {
        User user = userService.getUserInSecurityConfig();

        billing.setUserId(user.getId());
        billing.setNumber(generateNumber(user));
        Double subtotal = billing.getLineQuantities()
                .stream()
                .peek(lineQuantity -> lineQuantity.setDesignationId(designationService.createDesignationIfNotExist(lineQuantity.getDesignation())))
                .peek(designation -> designation.setDesignation(designationService.getById(designation.getDesignationId())))
                .filter(designation -> TypeDesignation.LINE.equals(designation.getDesignation().getTypeDesignation()))
                .map(designation ->  designation.getQuantity() * designation.getDesignation().getPrice())
                .reduce(0.0, Double::sum);
        Double tax = user.getTax() != 0 ? subtotal * (user.getTax() / 100) : 0;
        double discount = billing.getDiscountPercent() != 0 ? -(subtotal * (billing.getDiscountPercent() / 100)) : 0;

        billing.setTotalExcludingTaxes(subtotal);
        billing.setTotalIncludingTaxes(subtotal + tax - discount);
        billing.setTaxAmount(tax);
        billing.setDiscountAmount(discount);
        billing.setDate(LocalDate.now());
        billing.setDueDate(getDueDate(user, billing.getDate()));

        billing.setGeneratedFile(documentService.generateDocumentFromBilling(billing));

        return billingMapper.invoiceEntityToInvoice(billingRepository.save(billingMapper.invoiceToInvoiceEntity(billing)));
    }

    private String generateNumber(User user) {
        String prefixe = user.getId().substring(0, 4);
        return prefixe + this.counterService.getNextValueForUser(user);
    }

    private LocalDate getDueDate(User user, LocalDate date) {
        return date.plusDays(user.getMaxValidityBilling());
    }
}
