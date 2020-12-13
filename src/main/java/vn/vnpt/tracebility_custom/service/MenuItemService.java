package vn.vnpt.tracebility_custom.service;



import vn.vnpt.tracebility_custom.entity.Role;
import vn.vnpt.tracebility_custom.model.param.MenuParam;
import vn.vnpt.tracebility_custom.model.request.MenuItemNewRQ;
import vn.vnpt.tracebility_custom.model.request.MenuItemUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.model.response.PageRP;

import java.util.List;

public interface MenuItemService {

    PageRP findMenuItemByUserId(Long userId);

    ActionRP createMenuItem(MenuItemNewRQ menuItemNewRQ);

    ActionRP updateMenuItem(MenuItemUpdateRQ menuItemUpdateRQ);

    ActionRP deleteMenuItem(Long id);

    List<Role> getRoles(List<Long> roleIds);

    PageRP findById(Long id);

    PageRP findAll();

    PageRP findByProperties(MenuParam getListMenuParam);

    ActionRP deleteById(Long id);

    PageRP findMenuByRole(Long id);

}
