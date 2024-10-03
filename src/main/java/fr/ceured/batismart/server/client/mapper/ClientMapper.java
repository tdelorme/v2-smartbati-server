package fr.ceured.batismart.server.client.mapper;

import fr.ceured.batismart.server.client.entity.ClientEntity;
import fr.ceured.batismart.server.client.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientEntity clientToClientEntity(Client client);
    Client clientEntityToClient(ClientEntity clientEntity);

}
