package fr.ceured.batismart.server.billing.controller;

import fr.ceured.batismart.server.billing.model.Billing;
import fr.ceured.batismart.server.billing.service.BillingService;
import fr.ceured.batismart.server.commons.ApiResponse;
import fr.ceured.batismart.server.commons.PageableApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Billing>> findById(@PathVariable("id") final String id) {
        return ResponseEntity.ok(
                ApiResponse.<Billing>builder()
                        .data(billingService.getById(id))
                        .build()
        );
    }

    @GetMapping("/quote/page/{page}/size/{size}")
    public ResponseEntity<PageableApiResponse<List<Billing>>> findAllQuote(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);

        PageableApiResponse<List<Billing>> pageableApiResponse = new PageableApiResponse<>();

        Page<Billing> billingPage = billingService.getAllBillingQuote(pageable);
        pageableApiResponse.setTotalCount(billingPage.getTotalElements());
        pageableApiResponse.setData(billingPage.getContent());

        return ResponseEntity.ok(pageableApiResponse);
    }

    @GetMapping("/invoice/page/{page}/size/{size}")
    public ResponseEntity<PageableApiResponse<List<Billing>>> findAllInvoice(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);

        PageableApiResponse<List<Billing>> pageableApiResponse = new PageableApiResponse<>();

        Page<Billing> billingPage = billingService.getAllBillingInvoice(pageable);
        pageableApiResponse.setTotalCount(billingPage.getTotalElements());
        pageableApiResponse.setData(billingPage.getContent());

        return ResponseEntity.ok(pageableApiResponse);
    }

    @GetMapping("/invoice/paid/page/{page}/size/{size}")
    public ResponseEntity<PageableApiResponse<List<Billing>>> findAllInvoicePaid(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);

        PageableApiResponse<List<Billing>> pageableApiResponse = new PageableApiResponse<>();

        Page<Billing> billingPage = billingService.getAllBillingInvoicePaid(pageable);
        pageableApiResponse.setTotalCount(billingPage.getTotalElements());
        pageableApiResponse.setData(billingPage.getContent());

        return ResponseEntity.ok(pageableApiResponse);
    }

    @GetMapping("/invoice/not/paid/page/{page}/size/{size}")
    public ResponseEntity<PageableApiResponse<List<Billing>>> findAllInvoiceNotPaid(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);

        PageableApiResponse<List<Billing>> pageableApiResponse = new PageableApiResponse<>();

        Page<Billing> billingPage = billingService.getAllBillingInvoiceNotPaid(pageable);
        pageableApiResponse.setTotalCount(billingPage.getTotalElements());
        pageableApiResponse.setData(billingPage.getContent());

        return ResponseEntity.ok(pageableApiResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Billing>> createInvoice(@RequestBody Billing billing) throws ParseException {
        return ResponseEntity.ok(
                ApiResponse.<Billing>builder()
                        .data(billingService.createBilling(billing))
                        .build()
        );
    }

    @PostMapping("/transform/{id}")
    public ResponseEntity<ApiResponse<Boolean>> transform(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                ApiResponse.<Boolean>builder()
                        .data(billingService.transformToInvoice(id))
                        .build()
        );
    }

    @PostMapping("/paid/{id}")
    public ResponseEntity<ApiResponse<Boolean>> paidInvoice(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                ApiResponse.<Boolean>builder()
                        .data(billingService.paidInvoice(id))
                        .build()
        );
    }

    @PostMapping("/deposit/part/{id}/{amount}")
    public ResponseEntity<ApiResponse<Billing>> depositPart(@PathVariable("id") String id, @PathVariable("amount") Double amount) {
        return ResponseEntity.ok(
                ApiResponse.<Billing>builder()
                        .data(billingService.deposit(id, amount))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteInvoice(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                ApiResponse.<Boolean>builder()
                        .data(billingService.softDelete(id))
                        .build()
        );
    }

}
