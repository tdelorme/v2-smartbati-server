package fr.ceured.batismart.server.billing.controller;

import fr.ceured.batismart.server.billing.model.Billing;
import fr.ceured.batismart.server.billing.model.enums.BillingType;
import fr.ceured.batismart.server.billing.service.BillingService;
import fr.ceured.batismart.server.commons.ApiResponse;
import fr.ceured.batismart.server.commons.PageableApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/quote/page/{page}/size/{size}")
    public ResponseEntity<PageableApiResponse<List<Billing>>> findAllQuote(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);

        PageableApiResponse<List<Billing>> pageableApiResponse = new PageableApiResponse<>();

        Page<Billing> billingPage = billingService.getAllBillingByType(BillingType.QUOTE, pageable);
        pageableApiResponse.setTotalCount(billingPage.getTotalElements());
        pageableApiResponse.setData(billingPage.getContent());

        return ResponseEntity.ok(pageableApiResponse);
    }

    @GetMapping("/invoice/page/{page}/size/{size}")
    public ResponseEntity<PageableApiResponse<List<Billing>>> findAllInvoice(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);

        PageableApiResponse<List<Billing>> pageableApiResponse = new PageableApiResponse<>();

        Page<Billing> billingPage = billingService.getAllBillingByType(BillingType.INVOICE, pageable);
        pageableApiResponse.setTotalCount(billingPage.getTotalElements());
        pageableApiResponse.setData(billingPage.getContent());

        return ResponseEntity.ok(pageableApiResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Billing>> createInvoice(@RequestBody Billing billing) {
        return ResponseEntity.ok(
                ApiResponse.<Billing>builder()
                        .data(billingService.createBilling(billing))
                        .build()
        );
    }

}
