package vn.vnpt.tracebility_custom.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.vnpt.tracebility_custom.entity.MenuItem;
import vn.vnpt.tracebility_custom.entity.Role;
import vn.vnpt.tracebility_custom.exception.GeneralException;
import vn.vnpt.tracebility_custom.exception.NotFoundException;
import vn.vnpt.tracebility_custom.mapper.MenuItemMapper;
import vn.vnpt.tracebility_custom.mapper.UserMapper;
import vn.vnpt.tracebility_custom.model.param.MenuParam;
import vn.vnpt.tracebility_custom.model.request.MenuItemNewRQ;
import vn.vnpt.tracebility_custom.model.request.MenuItemUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.model.response.MenuItemRP;
import vn.vnpt.tracebility_custom.model.response.PageRP;
import vn.vnpt.tracebility_custom.repository.MenuItemRepository;
import vn.vnpt.tracebility_custom.repository.RoleRepository;
import vn.vnpt.tracebility_custom.service.MenuItemService;
import vn.vnpt.tracebility_custom.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageRP findById(Long id) {

        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_MENU_ITEM)
        );

        return PageRP.builder()
                .total(1)
                .content(menuItemMapper.toMenuItemRpFromMenuItem(menuItem))
                .build();
    }

    @Override
    public PageRP findAll() {

        List<MenuItem> menuItems = menuItemRepository.findAll();

        List<MenuItemRP> menuItemRPS = new ArrayList<>();

        for (MenuItem menuItem : menuItems) {

            if (menuItem.getParentId() == 0L) {

                MenuItemRP menuItemRP = menuItemMapper.toMenuItemRpFromMenuItem(menuItem);
                List<MenuItemRP> children = findChildren(menuItems, menuItem.getId());
                menuItemRP.setChildren(children);

                menuItemRPS.add(menuItemRP);
            }

        }
        final long totalItem = menuItems.size();

        return PageRP.builder()
                .total(menuItemRPS.size())
                .content(menuItemRPS)
                .build();
    }

    @Override
    public PageRP findByProperties(MenuParam getListMenuParam) {

        Pageable pageable = PageRequest.of(getListMenuParam.getPageNo() - 1, getListMenuParam.getPageSize(), Sort.by("id").descending());

        List<MenuItemRP> menuItemRPS = menuItemRepository.findByProperties(getListMenuParam.getKeyword(), pageable)
                .map(menuItemMapper::toMenuItemRpFromMenuItem)
                .getContent();

        return PageRP.builder()
                .total(menuItemRPS.size())
                .content(menuItemRPS)
                .build();
    }

    @Override
    public PageRP findMenuItemByUserId(Long userId) {

        List<MenuItem> menuItems = menuItemRepository.findMenuItemByUserId(userId);

        List<MenuItemRP> menuItemRPS = new ArrayList<>();

        for (MenuItem menuItem : menuItems) {

            if (menuItem.getParentId() == 0L) {

                MenuItemRP menuItemRP = menuItemMapper.toMenuItemRpFromMenuItem(menuItem);
                List<MenuItemRP> children = findChildren(menuItems, menuItem.getId());
                menuItemRP.setChildren(null);
                if (children.size() > 0) menuItemRP.setChildren(children);
                menuItemRPS.add(menuItemRP);
            }

        }
        final long totalItem = menuItems.size();

        return PageRP.builder()
                .total(totalItem)
                .content(menuItemRPS)
                .build();
    }

    @Override
    public ActionRP createMenuItem(MenuItemNewRQ menuItemNewRQ) {

        MenuItem menuItem = menuItemMapper.toMenuItemFromMEnuItemNewRQ(menuItemNewRQ);
        menuItem.setRoles(Collections.emptyList());

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, menuItemRepository.save(menuItem).getId())
                .build());
    }

    @Override
    public ActionRP updateMenuItem(MenuItemUpdateRQ menuItemUpdateRQ) {

        menuItemRepository.findById(menuItemUpdateRQ.getId()).orElseThrow(() ->
                new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.C404_MENU_ITEM)
        );

        MenuItem menuItem = menuItemMapper.toMenuItemFromMEnuItemUpdateRQ(menuItemUpdateRQ);
        menuItem.setId(menuItemUpdateRQ.getId());
        menuItem.setRoles(Collections.emptyList());

        if (menuItemUpdateRQ.getParentId() == null) menuItem.setParentId(0l);

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, menuItemRepository.save(menuItem).getId())
                .build());
    }

    @Override
    public ActionRP deleteMenuItem(Long id) {

        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.C404_MENU_ITEM)
        );
        menuItem.setIsActive(Boolean.FALSE);

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, menuItemRepository.save(menuItem).getId())
                .build());
    }

    @Override
    public List<Role> getRoles(List<Long> roleIds) {
        List<Role> roles = new ArrayList<>();
        roleIds.forEach(id -> {
                    Role role = roleRepository.findById(id).orElseThrow(() ->
                            new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.C404_ROLE));
                    roles.add(role);
                }
        );
        return roles;
    }


    private List<MenuItemRP> findChildren(List<MenuItem> menuItems, Long parentId) {

        List<MenuItemRP> menuItemRPS = new ArrayList<>();

        menuItems.forEach(item -> {
            if (item.getParentId() == parentId) {

                MenuItemRP menuItemRP = menuItemMapper.toMenuItemRpFromMenuItem(item);

                while (checkChildren(menuItems, item.getId())) {

                    menuItemRP.setChildren(findChildren(menuItems, item.getId()));
                }

                menuItemRPS.add(menuItemRP);
            }
        });

        return menuItemRPS;
    }

    boolean checkChildren(List<MenuItem> menuItems, long parentId) {

        boolean check = false;

        for (MenuItem menuItem : menuItems) {
            if (menuItem.getParentId() == parentId) check = true;
        }
        return check;
    }

    @Override
    public ActionRP deleteById(Long id) {

        if (menuItemRepository.findByID(id) == null) {
            throw new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.C404_MENU_ITEM_NOT_EXISTS);
        }

        menuItemRepository.deleteById(id);

        if (menuItemRepository.findByID(id) != null) {
            throw new GeneralException(Constant.RESPONSE.CODE.ERROR, Constant.RESPONSE.MESSAGE.ERROR);
        }

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, id)
                .build());
    }


    @Override
    public PageRP findMenuByRole(Long id) {

        Role role = roleRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_ROLE)
        );

        List<MenuItem> menus = new ArrayList<>();

        for(MenuItem menuItem:  role.getMenuItems()){

            if(!menus.contains(menuItem)) menus.add(menuItem);
        }

        List<Long> ids = new ArrayList<>();

        for(MenuItem menuItem:  role.getMenuItems()){

            if(!ids.contains(menuItem.getId())) ids.add(menuItem.getId());
        }

        return PageRP.builder()
                .total(menus.size())
                .content(ids)
                .build();
    }



}
