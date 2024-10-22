package fr.ceured.batismart.server.counter.repository;

import fr.ceured.batismart.server.counter.entity.CounterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CounterRepository extends MongoRepository<CounterEntity, String> {
    Optional<CounterEntity> findByUserId(String userId);
}
