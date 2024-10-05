package fr.ceured.batismart.server.designation.repository;

import fr.ceured.batismart.server.designation.entity.DesignationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DesignationRepository extends MongoRepository<DesignationEntity, String> {

    List<DesignationEntity> findAllByUserId(String userId);

}
