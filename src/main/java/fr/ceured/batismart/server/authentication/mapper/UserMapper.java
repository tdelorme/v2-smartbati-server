package fr.ceured.batismart.server.authentication.mapper;

import fr.ceured.batismart.server.authentication.entity.UserEntity;
import fr.ceured.batismart.server.authentication.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToDto(UserEntity userEntity);
    UserEntity mapToEntity(User user);

}
