package fr.ceured.batismart.server.designation.repository;

import fr.ceured.batismart.server.designation.model.Designation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DesignationRepository extends MongoRepository<Designation, String> {
}
