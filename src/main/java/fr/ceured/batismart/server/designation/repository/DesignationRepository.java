package fr.ceured.batismart.server.designation.repository;

import fr.ceured.batismart.server.designation.entity.DesignationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DesignationRepository extends MongoRepository<DesignationEntity, String> {

    List<DesignationEntity> findAllByUserId(String userId);
    Optional<DesignationEntity> findByName(String name);

    List<DesignationEntity> findByNameContainingAndUserId(String name, String userId);
}
