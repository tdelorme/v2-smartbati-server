package fr.ceured.batismart.server.invoice.repository;


import fr.ceured.batismart.server.invoice.entity.InvoiceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends MongoRepository<InvoiceEntity, String> {
}
