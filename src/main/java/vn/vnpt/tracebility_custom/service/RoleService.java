package vn.vnpt.tracebility_custom.service;


import vn.vnpt.tracebility_custom.model.param.RoleParam;
import vn.vnpt.tracebility_custom.model.request.RoleNewRQ;
import vn.vnpt.tracebility_custom.model.request.RoleUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.model.response.PageRP;

public interface RoleService {

    PageRP findlAll();

    ActionRP createRole(RoleNewRQ roleNewRQ);

    ActionRP updateRole(RoleUpdateRQ roleNewRQ);

    PageRP findByUserID(Long id);

    PageRP findByProperties(RoleParam getListRoleParam);

    PageRP findById(Long id);

    PageRP viewUser(Long id);

}
