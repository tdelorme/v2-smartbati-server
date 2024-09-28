package fr.ceured.batismart.server.email.repository;

import fr.ceured.batismart.server.email.entity.EmailConfirmationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailConfirmaitonRepository extends MongoRepository<EmailConfirmationEntity, String> {
    Optional<EmailConfirmationEntity> findByEmail(String email);
}
