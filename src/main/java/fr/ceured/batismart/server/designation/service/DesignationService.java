package fr.ceured.batismart.server.designation.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.commons.DoubleUtils;
import fr.ceured.batismart.server.commons.InvalidInputException;
import fr.ceured.batismart.server.designation.entity.DesignationEntity;
import fr.ceured.batismart.server.designation.exception.DesignationNotFoundException;
import fr.ceured.batismart.server.designation.mapper.DesignationMapper;
import fr.ceured.batismart.server.designation.model.Designation;
import fr.ceured.batismart.server.designation.repository.DesignationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

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
            || designation.getPrice() == null || designation.getPrice() <= 0) {
            throw new InvalidInputException();
        }

        User user = userService.getUserInSecurityConfig();

        DesignationEntity entity = designationMapper.designationToDesignationEntity(designation);
        entity.setUserId(user.getId());
        return designationMapper.designationEntityToDesignation(designationRepository.save(entity));
    }

    public Designation getById(String id) {
        return designationRepository.findById(id)
                .map(designationMapper::designationEntityToDesignation)
                .orElseThrow(() -> new DesignationNotFoundException(id));
    }

    public String createDesignationIfNotExist(Designation designation) throws ParseException {
        User user = userService.getUserInSecurityConfig();
        Optional<DesignationEntity> optionalDesignation = designationRepository.findByName(designation.getName());
        if (optionalDesignation.isPresent()) {
            return optionalDesignation.get().getId();
        } else {
            DesignationEntity entity = designationMapper.designationToDesignationEntity(designation);
            entity.setPrice(DoubleUtils.roundPrice(designation.getPrice()));
            entity.setUserId(user.getId());
            return designationRepository.save(entity).getId();
        }

    }

    public List<Designation> findAllDesignationFilterByName(String name) {

        User user = userService.getUserInSecurityConfig();

        return designationRepository.findByNameContainingAndUserId(name, user.getId())
                .stream()
                .map(designationMapper::designationEntityToDesignation)
                .toList();
    }
}
