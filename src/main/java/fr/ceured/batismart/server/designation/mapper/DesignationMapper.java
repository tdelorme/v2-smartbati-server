package fr.ceured.batismart.server.designation.mapper;

import fr.ceured.batismart.server.designation.entity.DesignationEntity;
import fr.ceured.batismart.server.designation.model.Designation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DesignationMapper {

    DesignationEntity designationToDesignationEntity(Designation designation);
    Designation designationEntityToDesignation(DesignationEntity designationEntity);

}
