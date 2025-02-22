package co.com.project.infraestructure.drivenadapters.jpa.user.transformer;

import co.com.project.domain.model.Role;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.RoleData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleDataTransformer {

    RoleDataTransformer INSTANCE = Mappers.getMapper(RoleDataTransformer.class);

    RoleData roleToRoleData(Role role);

    Role roleDataToRole(RoleData roleData);

}
