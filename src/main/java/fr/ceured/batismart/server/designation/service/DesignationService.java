package fr.ceured.batismart.server.designation.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.commons.InvalidInputException;
import fr.ceured.batismart.server.designation.entity.DesignationEntity;
import fr.ceured.batismart.server.designation.mapper.DesignationMapper;
import fr.ceured.batismart.server.designation.model.Designation;
import fr.ceured.batismart.server.designation.repository.DesignationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DesignationService {

    private final DesignationRepository designationRepository;
    private final UserService userService;

    private final DesignationMapper designationMapper;

    public List<Designation> findAllDesignations() {
        User user = userService.getUserInSecurityConfig();
        return designationRepository.findAllByUserId(user.getId())
                .stream()
                .map(designationMapper::designationEntityToDesignation)
                .toList();
    }

    public Designation createDesignation(Designation designation) {
        if (!StringUtils.hasText(designation.getName())
            || designation.getPriceExcludingTax() == null || designation.getPriceExcludingTax() <= 0
            || designation.getPriceIncludingTax() == null || designation.getPriceIncludingTax() <= 0) {
            throw new InvalidInputException();
        }

        User user = userService.getUserInSecurityConfig();

        DesignationEntity entity = designationMapper.designationToDesignationEntity(designation);
        entity.setUserId(user.getId());
        return designationMapper.designationEntityToDesignation(designationRepository.save(entity));
    }

}
