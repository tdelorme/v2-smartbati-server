package fr.ceured.batismart.server.designation.controller;

import fr.ceured.batismart.server.commons.ApiResponse;
import fr.ceured.batismart.server.designation.model.Designation;
import fr.ceured.batismart.server.designation.service.DesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/designation")
public class DesignationController {

    private final DesignationService designationService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Designation>>> findAllDesignation() {
        return ResponseEntity.ok(ApiResponse.<List<Designation>>builder()
                        .data(designationService.findAllDesignations())
                        .build());
    }

    @GetMapping("/{typeDesignation}/{name}")
    public ResponseEntity<ApiResponse<List<Designation>>> findAllDesignationByName(@PathVariable String typeDesignation, @PathVariable String name) {
        return ResponseEntity.ok(ApiResponse.<List<Designation>>builder()
                        .data(designationService.findAllDesignationFilterByNameAndTypeDesignation(name, typeDesignation))
                .build());
    }

    @GetMapping("/{typeDesignation}")
    public ResponseEntity<ApiResponse<List<Designation>>> findAllDesignationByCategory(@PathVariable String typeDesignation) {
        return ResponseEntity.ok(ApiResponse.<List<Designation>>builder()
                        .data(designationService.findAllDesignationsByTypeDesignation(typeDesignation))
                .build());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Designation>> createDesignation(@RequestBody Designation designation) {
        return ResponseEntity.ok(
            ApiResponse.<Designation>builder()
                    .data(designationService.createDesignation(designation))
                    .build()
        );
    }

}
