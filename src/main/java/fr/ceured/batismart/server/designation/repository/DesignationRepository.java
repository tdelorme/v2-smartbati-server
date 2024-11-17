package fr.ceured.batismart.server.designation.repository;

import fr.ceured.batismart.server.designation.entity.DesignationEntity;
import fr.ceured.batismart.server.designation.model.Designation;
import fr.ceured.batismart.server.designation.model.enums.TypeDesignation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DesignationRepository extends MongoRepository<DesignationEntity, String> {

    List<DesignationEntity> findAllByUserId(String userId);
    Optional<DesignationEntity> findByName(String name);

    List<DesignationEntity> findByNameContainingAndUserIdAndTypeDesignation(String name, String userId, TypeDesignation typeDesignation);

    List<Designation> findByUserIdAndTypeDesignation(String userId, TypeDesignation typeDesignation);
}
