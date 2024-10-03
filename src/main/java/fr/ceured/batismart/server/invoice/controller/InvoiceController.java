package fr.ceured.batismart.server.invoice.controller;

import fr.ceured.batismart.server.commons.ApiResponse;
import fr.ceured.batismart.server.invoice.model.Invoice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Invoice>>> findAllInvoice() {
        return ResponseEntity.ok(
                ApiResponse.<List<Invoice>>builder().build()
        );
    }

}
