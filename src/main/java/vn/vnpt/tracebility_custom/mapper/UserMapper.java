package vn.vnpt.tracebility_custom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import vn.vnpt.tracebility_custom.entity.User;
import vn.vnpt.tracebility_custom.model.request.UserSignUpRQ;
import vn.vnpt.tracebility_custom.model.request.UserUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.UserRP;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({})
    User toUserFromUserSignUp(UserSignUpRQ userSignUpRQ);

    UserRP toUserRpFromUser(User u);

    @Mappings({
            @Mapping(target = "roles", source = "roles", ignore = true),
    })
    User toUserFromUserUpdateRQ(UserUpdateRQ userUpdateRQ);
}
