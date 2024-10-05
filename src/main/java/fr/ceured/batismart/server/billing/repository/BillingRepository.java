package fr.ceured.batismart.server.billing.repository;


import fr.ceured.batismart.server.billing.entity.BillingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends MongoRepository<BillingEntity, String> {
}
