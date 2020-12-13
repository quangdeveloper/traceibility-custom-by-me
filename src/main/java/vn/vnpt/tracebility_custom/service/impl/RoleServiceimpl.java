package vn.vnpt.tracebility_custom.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.vnpt.tracebility_custom.entity.MenuItem;
import vn.vnpt.tracebility_custom.entity.Role;
import vn.vnpt.tracebility_custom.entity.User;
import vn.vnpt.tracebility_custom.exception.NotFoundException;
import vn.vnpt.tracebility_custom.mapper.RoleMapper;
import vn.vnpt.tracebility_custom.mapper.UserMapper;
import vn.vnpt.tracebility_custom.model.param.RoleParam;
import vn.vnpt.tracebility_custom.model.request.RoleNewRQ;
import vn.vnpt.tracebility_custom.model.request.RoleUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.model.response.PageRP;
import vn.vnpt.tracebility_custom.model.response.RoleRP;
import vn.vnpt.tracebility_custom.model.response.UserRP;
import vn.vnpt.tracebility_custom.repository.MenuItemRepository;
import vn.vnpt.tracebility_custom.repository.RoleRepository;
import vn.vnpt.tracebility_custom.repository.UserRepository;
import vn.vnpt.tracebility_custom.service.RoleService;
import vn.vnpt.tracebility_custom.util.Constant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceimpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageRP findlAll() {

        Pageable pageable = PageRequest.of(0, 1000, Sort.by("parentId").ascending());

        Page<Role> pageRoles = roleRepository.findAll(pageable);

        List<Role> roles = pageRoles.toList();

        List<RoleRP> roleRPList = new ArrayList<>();
        for (Role role : roles) {

            if (role.getParentId() == 0L) {

                RoleRP roleRP = roleMapper.toRoleRPFromRole(role);

                roleRP.setChildren(findChildren(roles, role.getId()));

                roleRPList.add(roleRP);
            }
        }

        final long total = pageRoles.getTotalElements();

        return PageRP.builder()
                .total(total)
                .content(roleRPList)
                .build();
    }

    @Override
    public ActionRP createRole(RoleNewRQ roleNewRQ) {

        Role role = roleMapper.toRoleFromRoleNewRQ(roleNewRQ);
        role.setUsers(Collections.emptyList());

        List<MenuItem> menuItems = new ArrayList<>();

        roleNewRQ.getMenuIds().forEach(id -> {
            MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_MENU_ITEM_NOT_EXISTS)
            );
            menuItems.add(menuItem);
        });
        role.setMenuItems(menuItems);

        if (roleNewRQ.getParentId() != 0) {
            Long parentLevel = roleRepository.findByID(roleNewRQ.getParentId()).getLevel();
            role.setLevel(parentLevel + 1);
        }

        if (roleNewRQ.getCode() == null || roleNewRQ.getCode().equals("")) {
            role.setCode(generateCode(roleNewRQ.getName()));
        }

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, roleRepository.save(role).getId())
                .build());
    }

    @Override
    public ActionRP updateRole(RoleUpdateRQ roleUpdateRQ) {

        Role role = roleRepository.findById(roleUpdateRQ.getId()).orElseThrow(() ->
                new NotFoundException(Constant.RESPONSE.MESSAGE.C404_ROLE_NOT_EXISTS)
        );

        Role newRole = roleMapper.toRoleFromRoleUpdateRQ(roleUpdateRQ);
        newRole.setId(roleUpdateRQ.getId());
        List<MenuItem> menuItems = new ArrayList<>();

        roleUpdateRQ.getMenuIds().forEach(id -> {
            MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_MENU_ITEM_NOT_EXISTS)
            );
            menuItems.add(menuItem);
        });
        newRole.setMenuItems(menuItems);

        if (newRole.getCode() == null || newRole.getCode().equals("")) {
            newRole.setCode(generateCode(newRole.getName()));
        }
        roleRepository.save(newRole);

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, 1)
                .build());
    }

    private String generateCode(String name) {

        StringBuilder sb = new StringBuilder();
        String arr[] = name.split(" ");
        for (String str : arr) {
            sb.append(str.substring(0, 1));
        }

        LocalDate now = LocalDate.now();

        sb.append(now.getDayOfMonth());
        sb.append(now.getMonthValue());
        sb.append(now.getYear());

        return sb.toString().toUpperCase();
    }


    private List<RoleRP> findChildren(List<Role> roles, Long parentId) {

        List<RoleRP> roleRPList = new ArrayList<>();

        roles.forEach(role -> {
            if (role.getParentId() == parentId) {

                RoleRP roleRP = roleMapper.toRoleRPFromRole(role);

                while (checkChildren(roles, role.getId())) {

                    roleRP.setChildren(findChildren(roles, role.getId()));
                }

                roleRPList.add(roleRP);
            }
        });

        return roleRPList;
    }

    boolean checkChildren(List<Role> roles, long parentId) {

        boolean check = false;

        for (Role role : roles) {
            if (role.getParentId() == parentId) check = true;
        }
        return check;
    }

    @Override
    public PageRP findByUserID(Long id) {

        User user = userRepository.findByID(id);

        List<Role> roles = new ArrayList<>();
        user.getRoles().forEach(role -> {
            if (!roles.contains(role)) roles.add(role);
        });

        List<Long> ids = new ArrayList<>();
        for (Role role : roles) {

            if (!ids.contains(role.getId())) ids.add(role.getId());
        }
        return PageRP.builder()
                .total(roles.size())
                .content(ids)
                .build();
    }

    @Override
    public PageRP findByProperties(RoleParam getListRoleParam) {

        Pageable pageable = PageRequest.of(getListRoleParam.getPageNo() - 1,
                getListRoleParam.getPageSize(),
                Sort.by("id").ascending());

        Page<Role> pageRoles = roleRepository.findByProperties(
                getListRoleParam.getKeyword(),
                getListRoleParam.getStatus(),
                pageable);

        final List<RoleRP> roleList = pageRoles.map(roleMapper::toRoleRPFromRole).getContent();

        final long total = pageRoles.getTotalElements();

        return PageRP.builder()
                .total(total)
                .content(roleList)
                .build();
    }


    private List<Role> findChildren(Long parentId) {

        List<Role> children = new ArrayList<>();

        List<Role> roleRoot = roleRepository.findAll();
        for (Role role : roleRoot) {

            if (role.getId() == parentId) children.add(role);
        }
        return children;
    }

    private boolean checkChildren(Long parentId) {

        boolean check = false;

        List<Role> roleRoot = roleRepository.findAll();
        for (Role role : roleRoot) {

            if (role.getId() == parentId) check = true;
        }
        return check;
    }

    @Override
    public PageRP findById(Long id) {

        Role role = roleRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_ROLE)
        );

        RoleRP roleRP = roleMapper.toRoleRPFromRole(role);
        roleRP.setMenus(findMenuIds(role));

        return PageRP.builder()
                .total(1)
                .content(roleRP)
                .build();
    }

    List<Long> findMenuIds(Role role) {

        List<MenuItem> menuItems = role.getMenuItems();

        List<Long> ids = new ArrayList<>();
        for (MenuItem m : menuItems) {
            if (!ids.contains(m.getId())) ids.add(m.getId());
        }
        return ids;
    }


    @Override
    public PageRP viewUser(Long id) {

        Role role = roleRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_ROLE)
        );

        List<User> users = role.getUsers();

        List<UserRP> userRPS = new ArrayList<>();

        for(User user: users) {
            userRPS.add(userMapper.toUserRpFromUser(user));
        }
        return PageRP.builder()
                .total(userRPS.size())
                .content(userRPS)
                .build();
    }

}
