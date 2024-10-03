package fr.ceured.batismart.server.client.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.client.entity.ClientEntity;
import fr.ceured.batismart.server.client.mapper.ClientMapper;
import fr.ceured.batismart.server.client.model.Client;
import fr.ceured.batismart.server.client.repository.ClientRepository;
import fr.ceured.batismart.server.commons.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserService userService;
    private final ClientMapper clientMapper;

    public Client createClient(Client client) {

        if ( null != client
                && !StringUtils.hasText(client.getEmail())
                && !StringUtils.hasText(client.getPhone())
                && !StringUtils.hasText(client.getAddress())
                && !StringUtils.hasText(client.getLastName())
                && !StringUtils.hasText(client.getFirstName())) {
            throw new InvalidInputException();
        }

        ClientEntity clientEntity = clientMapper.clientToClientEntity(client);
        User user = userService.getUserInSecurityConfig();

        clientEntity.setUserId(user.getId());

        return clientMapper.clientEntityToClient(clientRepository.save(clientEntity));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::clientEntityToClient)
                .toList();
    }

    public Page<Client> getAllClientsByPage(Pageable pageable) {
        User user = userService.getUserInSecurityConfig();
        return clientRepository.findAllByUserId(user.getId(), pageable)
                .map(clientMapper::clientEntityToClient);
    }

}
