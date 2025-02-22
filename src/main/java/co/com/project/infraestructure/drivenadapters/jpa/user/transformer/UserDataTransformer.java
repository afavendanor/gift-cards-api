package co.com.project.infraestructure.drivenadapters.jpa.user.transformer;

import co.com.project.domain.model.User;
import co.com.project.infraestructure.drivenadapters.jpa.user.entities.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface UserDataTransformer {

    UserDataTransformer INSTANCE = Mappers.getMapper(UserDataTransformer.class);

    User userDataToUser(UserData userData);

    UserData userToUserData(User user);

    List<User> userDataListToUserList(List<UserData> userDataList);

}
