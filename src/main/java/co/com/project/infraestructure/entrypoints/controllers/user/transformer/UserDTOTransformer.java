package co.com.project.infraestructure.entrypoints.controllers.user.transformer;

import co.com.project.application.dtos.UserDTO;
import co.com.project.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDTOTransformer {

    UserDTOTransformer INSTANCE = Mappers.getMapper(UserDTOTransformer.class);

    User userDTOToUser(UserDTO userDTO);

    UserDTO userToUserDTO(User user);

    List<UserDTO> userListToUserDTOList(List<User> userList);

}