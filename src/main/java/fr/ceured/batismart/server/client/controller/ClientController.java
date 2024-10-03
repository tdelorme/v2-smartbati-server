package fr.ceured.batismart.server.client.controller;

import fr.ceured.batismart.server.client.model.Client;
import fr.ceured.batismart.server.client.service.ClientService;
import fr.ceured.batismart.server.commons.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Client>> create(@RequestBody Client client) {
        return ResponseEntity.ok(
                ApiResponse.<Client>builder()
                        .data(clientService.createClient(client))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Client>>> getAllClients() {
        return ResponseEntity.ok(
                ApiResponse.<List<Client>>builder()
                        .data(clientService.getAllClients())
                        .build()
        );
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<ApiResponse<List<Client>>> getAllClientsByPage(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                ApiResponse.<List<Client>>builder()
                        .data(clientService.getAllClientsByPage(pageable))
                        .build()
        );
    }

}
