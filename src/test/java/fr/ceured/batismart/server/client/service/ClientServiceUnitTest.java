package fr.ceured.batismart.server.client.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.client.entity.ClientEntity;
import fr.ceured.batismart.server.client.mapper.ClientMapper;
import fr.ceured.batismart.server.client.model.Client;
import fr.ceured.batismart.server.client.repository.ClientRepository;
import fr.ceured.batismart.server.commons.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClientServiceUnitTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserService userService;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @ParameterizedTest
    @NullAndEmptySource
    void should_throw_input_exception_when_email_is_null_or_empty(String email) {
        //GIVEN
        Client client = Client.builder()
                .email(email)
                .build();
        //WHEN & THEN
        InvalidInputException iie = Assertions.assertThrows(InvalidInputException.class, () -> clientService.createClient(client));
        Assertions.assertEquals(InvalidInputException.INVALID_INPUT, iie.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_throw_input_exception_when_phone_is_null_or_empty(String phone) {
        //GIVEN
        Client client = Client.builder()
                .email(phone)
                .build();
        //WHEN & THEN
        InvalidInputException iie = Assertions.assertThrows(InvalidInputException.class, () -> clientService.createClient(client));
        Assertions.assertEquals(InvalidInputException.INVALID_INPUT, iie.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_throw_input_exception_when_address_is_null_or_empty(String address) {
        //GIVEN
        Client client = Client.builder()
                .address(address)
                .build();
        //WHEN & THEN
        InvalidInputException iie = Assertions.assertThrows(InvalidInputException.class, () -> clientService.createClient(client));
        Assertions.assertEquals(InvalidInputException.INVALID_INPUT, iie.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_throw_input_exception_when_lastname_is_null_or_empty(String lastname) {
        //GIVEN
        Client client = Client.builder()
                .lastName(lastname)
                .build();
        //WHEN & THEN
        InvalidInputException iie = Assertions.assertThrows(InvalidInputException.class, () -> clientService.createClient(client));
        Assertions.assertEquals(InvalidInputException.INVALID_INPUT, iie.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void should_throw_input_exception_when_firstname_is_null_or_empty(String firstname) {
        //GIVEN
        Client client = Client.builder()
                .firstName(firstname)
                .build();
        //WHEN & THEN
        InvalidInputException iie = Assertions.assertThrows(InvalidInputException.class, () -> clientService.createClient(client));
        Assertions.assertEquals(InvalidInputException.INVALID_INPUT, iie.getMessage());
    }

    @Test
    void should_return_client_created_when_all_is_ok() {
        //GIVEN
        mockUser();

        Client client = buildClient();
        ClientEntity clientEntity = buildClientEntity();
        Mockito.when(clientMapper.clientToClientEntity(client)).thenReturn(clientEntity);
        Mockito.when(clientMapper.clientEntityToClient(clientEntity)).thenReturn(client);
        Mockito.when(clientRepository.save(clientEntity)).thenReturn(clientEntity);
        // WHEN
        Client clientReturned = clientService.createClient(client);
        // THEN
        Assertions.assertNotNull(clientReturned);
        Assertions.assertEquals("test@test.fr", clientReturned.getEmail());
        Assertions.assertEquals("test address", clientReturned.getAddress());
        Assertions.assertEquals("test phone", clientReturned.getPhone());
        Assertions.assertEquals("test firstname", clientReturned.getFirstName());
        Assertions.assertEquals("test lastname", clientReturned.getLastName());
    }

    @Test
    void should_return_list_of_client() {
        //GIVEN
        Client client = buildClient();

        ClientEntity clientEntity = buildClientEntity();
        List<ClientEntity> clientEntities = new ArrayList<>();
        clientEntities.add(clientEntity);
        clientEntities.add(clientEntity);
        //WHEN
        Mockito.when(clientRepository.findAll()).thenReturn(clientEntities);
        Mockito.when(clientMapper.clientEntityToClient(clientEntity)).thenReturn(client);
        List<Client> clientList = clientService.getAllClients();
        //THEN
        Assertions.assertNotNull(clientList);
        Assertions.assertEquals(2, clientList.size());
    }

    @Test
    void should_return_list_of_client_from_page_request() {
        //GIVEN
        User user = mockUser();

        Client client = buildClient();

        ClientEntity clientEntity = buildClientEntity();

        List<ClientEntity> clientEntities = new ArrayList<>();
        clientEntities.add(clientEntity);
        clientEntities.add(clientEntity);
        Page<ClientEntity> clientEntitiesPage = new PageImpl<>(clientEntities);

        Pageable pageable = PageRequest.of(0, 10);

        //WHEN
        Mockito.when(clientRepository.findAllByUserId(user.getId(), pageable)).thenReturn(clientEntitiesPage);
        Mockito.when(clientMapper.clientEntityToClient(clientEntity)).thenReturn(client);

        Page<Client> clients = clientService.getAllClientsByPage(pageable);

        Assertions.assertNotNull(clients);
        Assertions.assertEquals(2, clients.getTotalElements());

    }

    private static ClientEntity buildClientEntity() {
        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setEmail("test@test.fr");
        clientEntity.setAddress("test address");
        clientEntity.setPhone("test phone");
        clientEntity.setLastName("test lastname");
        clientEntity.setFirstName("test firstname");
        return clientEntity;
    }

    private static Client buildClient() {
        return Client.builder()
                .email("test@test.fr")
                .address("test address")
                .phone("test phone")
                .lastName("test lastname")
                .firstName("test firstname")
                .build();
    }

    private User mockUser() {
        User user = new User();
        user.setId("id");
        user.setEmail("username");
        user.setPassword("password");
        user.setActive(true);
        user.setPhone("phone");
        user.setFirstName("firstname");
        user.setLastName("lastname");

        Mockito.when(userService.getUserInSecurityConfig()).thenReturn(user);

        return user;
    }
}
