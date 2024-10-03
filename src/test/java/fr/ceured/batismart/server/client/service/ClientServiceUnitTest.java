package fr.ceured.batismart.server.client.service;

import fr.ceured.batismart.server.authentication.exception.EmailNotFoundException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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
    void should_throw_email_exception_when_email_is_not_found() {
        //GIVEN
        mockSpringSecurity();

        Mockito.when(userService.getByEmail("username")).thenThrow(new EmailNotFoundException());

        Client client = buildClient();
        ClientEntity clientEntity = buildClientEntity();

        Mockito.when(clientMapper.clientToClientEntity(client)).thenReturn(clientEntity);

        //WHEN & THEN
        EmailNotFoundException enfe = Assertions.assertThrows(EmailNotFoundException.class, () -> clientService.createClient(client));
        Assertions.assertEquals(EmailNotFoundException.EMAIL_NOT_FOUND, enfe.getMessage());
    }

    @Test
    void should_return_client_created_when_all_is_ok() {
        //GIVEN
        mockSpringSecurity();

        User user = new User();
        user.setEmail("username");
        user.setPassword("password");
        user.setActive(true);
        user.setPhone("phone");
        user.setFirstName("firstname");
        user.setLastName("lastname");

        // WHEN
        Mockito.when(userService.getByEmail("username")).thenReturn(user);

        Client client = buildClient();
        ClientEntity clientEntity = buildClientEntity();
        Mockito.when(clientMapper.clientToClientEntity(client)).thenReturn(clientEntity);
        Mockito.when(clientMapper.clientEntityToClient(clientEntity)).thenReturn(client);
        Mockito.when(clientRepository.save(clientEntity)).thenReturn(clientEntity);

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
        Client client = buildClient();

        ClientEntity clientEntity = buildClientEntity();

        List<ClientEntity> clientEntities = new ArrayList<>();
        clientEntities.add(clientEntity);
        clientEntities.add(clientEntity);
        Page<ClientEntity> clientEntitiesPage = new PageImpl<>(clientEntities);

        Pageable pageable = PageRequest.of(0, 10);

        //WHEN
        Mockito.when(clientRepository.findAll(pageable)).thenReturn(clientEntitiesPage);
        Mockito.when(clientMapper.clientEntityToClient(clientEntity)).thenReturn(client);

        List<Client> clients = clientService.getAllClientsByPage(pageable);

        Assertions.assertNotNull(clients);
        Assertions.assertEquals(2, clients.size());

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

    private static void mockSpringSecurity() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn("username");
    }

}
