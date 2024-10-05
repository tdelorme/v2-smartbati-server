package fr.ceured.batismart.server.designation.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.service.UserService;
import fr.ceured.batismart.server.commons.InvalidInputException;
import fr.ceured.batismart.server.designation.entity.DesignationEntity;
import fr.ceured.batismart.server.designation.mapper.DesignationMapper;
import fr.ceured.batismart.server.designation.model.Designation;
import fr.ceured.batismart.server.designation.repository.DesignationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DesignationServiceUnitTest {

    @Mock
    private DesignationRepository designationRepository;
    @Mock
    private UserService userService;
    @Mock
    private DesignationMapper designationMapper;

    @InjectMocks
    private DesignationService designationService;

    @DisplayName("Doit retourner une liste de designation quand il est appelé")
    @Test
    void shouldReturnAllDesignationsMapped_WhenCalled() {
        User user = mockUser();

        DesignationEntity designationEntity = new DesignationEntity();
        designationEntity.setId("id");
        designationEntity.setName("name");
        designationEntity.setPriceExcludingTax(10.0);
        designationEntity.setPriceIncludingTax(25.0);

        Designation designation = Designation.builder()
                .id("id")
                .name("name")
                .priceExcludingTax(10.0)
                .priceIncludingTax(25.0)
                .build();

        List<DesignationEntity> designationEntities = List.of(designationEntity, designationEntity);
        Mockito.when(designationRepository.findAllByUserId(user.getId())).thenReturn(designationEntities);

        Mockito.when(designationMapper.designationEntityToDesignation(designationEntity)).thenReturn(designation);

        List<Designation> designations = designationService.findAllDesignations();

        Assertions.assertNotNull(designations);
        Assertions.assertEquals(designationEntities.size(), designations.size());
    }

    @DisplayName("Doit retourner une exception quand le nom est null ou vide")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowAnException_whenNameIsNullOrEmpty(String name) {
        Designation designation = Designation.builder()
                .name(name)
                .build();
        Assertions.assertThrows(InvalidInputException.class, () -> designationService.createDesignation(designation));
    }

    @DisplayName("Doit retourner une exception quand le prix HT est 0 ou negatif")
    @ParameterizedTest
    @ValueSource(doubles = {0, -1})
    void shouldThrowAnException_whenPriceExcludingTaxIsEquals0OrNegative(double price) {
        Designation designation = Designation.builder()
                .priceExcludingTax(price)
                .build();
        Assertions.assertThrows(InvalidInputException.class, () -> designationService.createDesignation(designation));
    }

    @DisplayName("Doit retourner une exception quand le prix TTC est 0 ou negatif")
    @ParameterizedTest
    @ValueSource(doubles = {0, -1})
    void shouldThrowAnException_whenPriceIncludingTaxIsEquals0OrNegative(double price) {
        Designation designation = Designation.builder()
                .priceExcludingTax(price)
                .build();
        Assertions.assertThrows(InvalidInputException.class, () -> designationService.createDesignation(designation));
    }

    @DisplayName("Doit retourner une désignation quand la demande de création est faite")
    @Test
    void shouldReturnDesignation_whenCallCreateDesignation() {
        mockUser();
        DesignationEntity designationEntity = DesignationEntity.builder()
                .id("id")
                .name("name")
                .priceExcludingTax(10.0)
                .priceIncludingTax(25.0)
                .build();

        Designation designation = Designation.builder()
                .id("id")
                .name("name")
                .priceExcludingTax(10.0)
                .priceIncludingTax(25.0)
                .build();

        Mockito.when(designationRepository.save(designationEntity)).thenReturn(designationEntity);
        Mockito.when(designationMapper.designationEntityToDesignation(designationEntity)).thenReturn(designation);
        Mockito.when(designationMapper.designationToDesignationEntity(designation)).thenReturn(designationEntity);

        Designation designationResult = designationService.createDesignation(designation);

        Assertions.assertNotNull(designationResult);
        Assertions.assertEquals(designationEntity.getId(), designationResult.getId());
        Assertions.assertEquals(designationEntity.getName(), designationResult.getName());
        Assertions.assertEquals(designationEntity.getPriceExcludingTax(), designationResult.getPriceExcludingTax());
        Assertions.assertEquals(designationEntity.getPriceIncludingTax(), designationResult.getPriceIncludingTax());

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
