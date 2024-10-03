package fr.ceured.batismart.server.client.controller;

import fr.ceured.batismart.server.client.model.Client;
import fr.ceured.batismart.server.client.service.ClientService;
import fr.ceured.batismart.server.commons.ApiResponse;
import lombok.RequiredArgsConstructor;
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

}
