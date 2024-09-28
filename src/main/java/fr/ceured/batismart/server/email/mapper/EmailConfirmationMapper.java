package fr.ceured.batismart.server.email.mapper;

import fr.ceured.batismart.server.email.entity.EmailConfirmationEntity;
import fr.ceured.batismart.server.email.model.EmailConfirmation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailConfirmationMapper {
    EmailConfirmation mapToDto(EmailConfirmationEntity entity);
    EmailConfirmationEntity mapToEntity(EmailConfirmation entity);
}
