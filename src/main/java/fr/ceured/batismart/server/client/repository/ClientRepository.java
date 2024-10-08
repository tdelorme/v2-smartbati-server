package fr.ceured.batismart.server.client.repository;

import fr.ceured.batismart.server.client.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<ClientEntity, String> {

    Page<ClientEntity> findAllByUserId(String userId, Pageable pageable);

}
