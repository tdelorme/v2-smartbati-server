package fr.ceured.batismart.server.billing.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.billing.exception.BillingNotFoundException;
import fr.ceured.batismart.server.billing.exception.ExceedTotalException;
import fr.ceured.batismart.server.billing.mapper.BillingMapper;
import fr.ceured.batismart.server.billing.model.Billing;
import fr.ceured.batismart.server.billing.model.enums.BillingType;
import fr.ceured.batismart.server.billing.repository.BillingRepository;
import fr.ceured.batismart.server.client.service.ClientService;
import fr.ceured.batismart.server.commons.DoubleUtils;
import fr.ceured.batismart.server.counter.service.CounterService;
import fr.ceured.batismart.server.designation.model.enums.TypeDesignation;
import fr.ceured.batismart.server.designation.service.DesignationService;
import fr.ceured.batismart.server.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    public Billing getById(String id) {
        return billingRepository.findById(id)
                .map(billingMapper::invoiceEntityToInvoice)
                .map(billing -> {
                    billing.setClient(clientService.getClientById(billing.getClientId()));
                    return billing;
                })
                .orElseThrow(() -> new BillingNotFoundException(id));
    }

    private Billing update(Billing billing) {
        return billingMapper.invoiceEntityToInvoice(billingRepository.save(billingMapper.invoiceToInvoiceEntity(billing)));
    }

    public Page<Billing> getAllBillingQuote(Pageable pageable) {
        User user = userService.getUserInSecurityConfig();

        return this.billingRepository.findAllByTypeAndUserIdAndDeletedFalseOrderByIdDesc(BillingType.QUOTE, user.getId(), pageable)
                .map(billingMapper::invoiceEntityToInvoice)
                .map(billing -> {
                    billing.setClient(clientService.getClientById(billing.getClientId()));
                    return billing;
                });
    }

    public Page<Billing> getAllBillingInvoice(Pageable pageable) {
        User user = userService.getUserInSecurityConfig();

        return this.billingRepository.findAllByTypeInAndUserIdAndDeletedFalseOrderByIdDesc(List.of(BillingType.INVOICE, BillingType.INVOICE_PAID), user.getId(), pageable)
                .map(billingMapper::invoiceEntityToInvoice)
                .map(billing -> {
                    billing.setClient(clientService.getClientById(billing.getClientId()));
                    return billing;
                });
    }

    public Page<Billing> getAllBillingInvoicePaid(Pageable pageable) {
        User user = userService.getUserInSecurityConfig();

        return this.billingRepository.findAllByTypeInAndUserIdAndDeletedFalseOrderByIdDesc(List.of(BillingType.INVOICE_PAID), user.getId(), pageable)
                .map(billingMapper::invoiceEntityToInvoice)
                .map(billing -> {
                    billing.setClient(clientService.getClientById(billing.getClientId()));
                    return billing;
                });
    }

    public Page<Billing> getAllBillingInvoiceNotPaid(Pageable pageable) {
        User user = userService.getUserInSecurityConfig();

        return this.billingRepository.findAllByTypeInAndUserIdAndDeletedFalseOrderByIdDesc(List.of(BillingType.INVOICE), user.getId(), pageable)
                .map(billingMapper::invoiceEntityToInvoice)
                .map(billing -> {
                    billing.setClient(clientService.getClientById(billing.getClientId()));
                    return billing;
                });
    }

    public Billing createBilling(Billing billing) throws ParseException {
        User user = userService.getUserInSecurityConfig();

        billing.setUserId(user.getId());
        billing.setNumber(generateNumber(user));
        double subtotal = billing.getLineQuantities()
                .stream()
                .peek(lineQuantity -> {
                    try {
                        lineQuantity.setDesignationId(designationService.upsertDesignationAndGetId(lineQuantity.getDesignation()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .peek(designation -> designation.setDesignation(designationService.getById(designation.getDesignationId())))
                .filter(designation -> TypeDesignation.LINE.equals(designation.getDesignation().getTypeDesignation()))
                .map(designation ->  designation.getQuantity() * designation.getDesignation().getPrice())
                .reduce(0.0, Double::sum);
        double tax = user.getTax() != 0 ? subtotal * (user.getTax() / 100) : 0;
        double discount = billing.getDiscountPercent() != 0 ? -(subtotal * (billing.getDiscountPercent() / 100)) : 0;

        billing.setTotalExcludingTaxes(DoubleUtils.roundPrice(subtotal));
        billing.setTotalIncludingTaxes(DoubleUtils.roundPrice(subtotal + tax - discount));
        billing.setTaxAmount(DoubleUtils.roundPrice(tax));
        billing.setDiscountAmount(DoubleUtils.roundPrice(discount));
        billing.setDate(LocalDate.now());
        billing.setDueDate(getDueDate(user, billing.getDate()));

        billing.setGeneratedFile(documentService.generateDocumentFromBilling(billing));
        billing.setDeleted(Boolean.FALSE);

        return billingMapper.invoiceEntityToInvoice(billingRepository.save(billingMapper.invoiceToInvoiceEntity(billing)));
    }

    private String generateNumber(User user) {
        String prefixe = user.getId().substring(0, 4);
        return prefixe + this.counterService.getNextValueForUser(user);
    }

    private LocalDate getDueDate(User user, LocalDate date) {
        return date.plusDays(user.getMaxValidityBilling());
    }

    public Boolean softDelete(String id) {
        Billing billing = getById(id);

        billing.setDeleted(true);
        update(billing);

        return true;
    }

    public Boolean transformToInvoice(String id) {
        Billing billing = getById(id);

        billing.setType(BillingType.INVOICE);
        billing.setGeneratedFile(documentService.generateDocumentFromBilling(billing));

        update(billing);

        return true;
    }

    public Boolean paidInvoice(String id) {
        Billing billing = getById(id);

        billing.setType(BillingType.INVOICE_PAID);

        update(billing);

        return true;
    }

    public Billing deposit(String id, Double amount) {
        Billing billing = getById(id);

        Double deposit = 0d;

        if (billing.getDeposit() != null) {
            deposit = billing.getDeposit();
        }

        deposit += amount;

        if ((billing.getTotalIncludingTaxes() - deposit) < 0) {
            throw new ExceedTotalException();
        }

        billing.setDeposit(deposit);


        if (billing.getDeposit().equals(billing.getTotalIncludingTaxes())) {
            billing.setType(BillingType.INVOICE_PAID);
        }

        Billing billingUpdated = update(billing);
        billingUpdated.setClient(clientService.getClientById(billing.getClientId()));


        return billingUpdated;
    }
}
