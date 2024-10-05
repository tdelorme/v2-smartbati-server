package fr.ceured.batismart.server.billing.controller;

import fr.ceured.batismart.server.billing.model.Billing;
import fr.ceured.batismart.server.commons.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class BillingController {

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Billing>>> findAllInvoice() {
        return ResponseEntity.ok(
                ApiResponse.<List<Billing>>builder().build()
        );
    }

}
