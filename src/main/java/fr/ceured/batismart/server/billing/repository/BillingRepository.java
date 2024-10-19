package fr.ceured.batismart.server.billing.repository;


import fr.ceured.batismart.server.billing.entity.BillingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends MongoRepository<BillingEntity, String> {

    List<BillingEntity> findAllByUserId(String userId);

}
