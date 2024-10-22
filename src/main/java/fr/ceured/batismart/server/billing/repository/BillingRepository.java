package fr.ceured.batismart.server.billing.repository;


import fr.ceured.batismart.server.billing.entity.BillingEntity;
import fr.ceured.batismart.server.billing.model.enums.BillingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends MongoRepository<BillingEntity, String> {

    List<BillingEntity> findAllByUserId(String userId);

    Page<BillingEntity> findAllByTypeAndUserId(BillingType type, String userId, Pageable pageable);
}
