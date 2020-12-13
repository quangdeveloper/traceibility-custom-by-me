package vn.vnpt.tracebility_custom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import vn.vnpt.tracebility_custom.entity.Role;
import vn.vnpt.tracebility_custom.model.request.RoleNewRQ;
import vn.vnpt.tracebility_custom.model.request.RoleUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.RoleRP;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    @Mappings({
            @Mapping(source = "name", target = "key"),
            @Mapping(source = "name", target = "label"),
    })
    RoleRP toRoleRPFromRole(Role role);

    List<RoleRP> toRoleRPFromRoles(List<Role> roles);

    Role toRoleFromRoleNewRQ(RoleNewRQ roleNewRQ);

    Role toRoleFromRoleUpdateRQ(RoleUpdateRQ roleUpdateRQ);
}
