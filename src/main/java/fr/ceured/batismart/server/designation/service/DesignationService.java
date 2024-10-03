package fr.ceured.batismart.server.designation.service;

import fr.ceured.batismart.server.designation.model.Designation;
import fr.ceured.batismart.server.designation.repository.DesignationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DesignationService {

    private final DesignationRepository designationRepository;

    public List<Designation> findAllDesignations() {
        return designationRepository.findAll();
    }

}
