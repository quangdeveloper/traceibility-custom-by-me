package vn.vnpt.tracebility_custom.service.impl;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.tracebility_custom.config.ApplicationProperties;
import vn.vnpt.tracebility_custom.entity.*;
import vn.vnpt.tracebility_custom.exception.GeneralException;
import vn.vnpt.tracebility_custom.exception.NotFoundException;
import vn.vnpt.tracebility_custom.jwt.JWTTokenProvider;
import vn.vnpt.tracebility_custom.mapper.MenuItemMapper;
import vn.vnpt.tracebility_custom.mapper.RoleMapper;
import vn.vnpt.tracebility_custom.mapper.UserMapper;
import vn.vnpt.tracebility_custom.model.param.PageParam;
import vn.vnpt.tracebility_custom.model.param.UserParam;
import vn.vnpt.tracebility_custom.model.request.*;
import vn.vnpt.tracebility_custom.model.response.*;
import vn.vnpt.tracebility_custom.repository.*;
import vn.vnpt.tracebility_custom.security.UserPrincipal;
import vn.vnpt.tracebility_custom.service.MenuItemService;
import vn.vnpt.tracebility_custom.service.UploadService;
import vn.vnpt.tracebility_custom.service.UserService;
import vn.vnpt.tracebility_custom.util.Constant;
import vn.vnpt.tracebility_custom.util.FileUtil;
import vn.vnpt.tracebility_custom.util.GenerateCode;
import vn.vnpt.tracebility_custom.util.SecurityUtil;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import vn.vnpt.tracebility_custom.model.response.UserLoginRP;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${config.mail.username}")
    private String email;

    @Value("${config.mail.properties.mail.amount}")
    private Integer amount;

    @Value("${app.default.timeContain}")
    private Long containSecond;

    @Value("${app.default.password}")
    private String defaultPassword;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAvatarRepository avatarRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CheckService checkService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private BussinessTypeRepository bussinessTypeRepository;

    @Override
    public UserLoginRP login(UserLoginRQ user) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtSecure = jwtTokenProvider.generateToken((UserPrincipal) authentication.getPrincipal());
        String refreshToken = jwtTokenProvider.generateRefreshToken((UserPrincipal) authentication.getPrincipal());

        User u = userRepository.findByUserName(user.getUsername());

        List<MenuItemRP> menuItemRPs = menuItemService.findMenuItemByUserId(u.getId()).toList();

        UserLoginRP userLoginRP = UserLoginRP.builder()
                .id(u.getId())
                .avatarPath("dfhsdhewber")
                .fullname(u.getFullname())
                .bussinessType(getBussinessId(u.getBussinessTypes()))
                .typeUser(u.getTypeUser())
                .jwt(jwtSecure)
                .roles(getRoleId(u.getRoles()))
                .menu(menuItemRPs)
                .isLogDiary(getLogDiary(u.getBussinessTypes()))
                .build();

        this.setAccessToken(u.getUsername(), jwtSecure, refreshToken);

        return userLoginRP;
    }


    @Override
    public User findByUsername(String username) {

        User user = userRepository.findByUserName(username);
        if (user == null) {
            log.info("Khong ton tai user nay trong he thong " + username);
            throw new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.C404_USER);
        }
        return user;
    }

    @Autowired
    private UploadService uploadService;


    @Override
    public ActionRP register(UserSignUpRQ userSignUpRQ) {

        User oloUser = userRepository.findByUserNameOrPhoneOrEmail(userSignUpRQ.getUsername(), userSignUpRQ.getPhone(), userSignUpRQ.getEmail());

        if (oloUser != null)
            throw new GeneralException(Constant.RESPONSE.CODE.C409, Constant.RESPONSE.MESSAGE.C409_USER_CUSTOM);

        User newUser = User.builder()
                .password(passwordEncoder.encode("e10adc3949ba59abbe56e057f20f883e"))//123456
                .salt(passwordEncoder.encode("e10adc3949ba59abbe56e057f20f883e"))
                .username(userSignUpRQ.getUsername())
                .fullname(userSignUpRQ.getFullname())
                .email(userSignUpRQ.getEmail())
                .phone(userSignUpRQ.getPhone())
                .provinceId(userSignUpRQ.getProvinceId())
                .districtId(userSignUpRQ.getDistrictId())
                .areaId(userSignUpRQ.getAreaId())
                .info(userSignUpRQ.getInfo())
                .bussinessTypes(getBussinessType(userSignUpRQ.getTypes()))
                .roles(new ArrayList<>())
                .typeUser("CUSTOMER")
                .build();
        Long id = userRepository.save(newUser).getId();

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.UPLOAD_KEY, uploadService.progressUpload(id, "AVATAR"))
                .build());
    }


    @Override
    public ActionRP createUserForAdmin(UserNewRQ userNewRQ) {

        User oloUser = userRepository.findByUserNameOrPhoneOrEmail(userNewRQ.getUsername(), userNewRQ.getPhone(), userNewRQ.getEmail());

        if (oloUser != null)
            throw new GeneralException(Constant.RESPONSE.CODE.C409, Constant.RESPONSE.MESSAGE.C409_USER_CUSTOM);

        User newUser = User.builder()
                .fullname(userNewRQ.getFullname())
                .username(userNewRQ.getUsername())
                .email(userNewRQ.getEmail())
                .phone(userNewRQ.getPhone())
                .build();

        List<Role> roles = new ArrayList<>();
        userNewRQ.getRoles().forEach(role ->
                roles.add(roleRepository.findById(role).get())
        );

        newUser.setRoles(roles);
        newUser.setIsActive(Boolean.TRUE);
        newUser.setPassword(passwordEncoder.encode(userNewRQ.getPassword()));

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, userRepository.save(newUser).getId())
                .build());

    }

    @Override
    public PageRP findAll(PageParam p) {

        Pageable pageable = PageRequest.of(p.getPageNo() - 1, p.getPageSize(), Sort.by("id").ascending());

        final Page<User> page = userRepository.findAll(pageable);

        List<UserRP> list = page.map(userMapper::toUserRpFromUser).getContent();

        final long total = page.getTotalElements();

        return PageRP.builder()
                .content(list)
                .total(total)
                .build();
    }

    @Override
    public PageRP findByMultiProperties(UserParam getListUserParam) {

        Pageable pageable = PageRequest.of(
                getListUserParam.getPageNo() - 1,
                getListUserParam.getPageSize(),
                Sort.by("id").ascending());

        final Page<User> page = userRepository.findByMultiProperties(
                pageable,
                getListUserParam.getKeyword(),
                getListUserParam.getStatus()
        );

        List<UserRP> list = page.map(userMapper::toUserRpFromUser).getContent();

        final long total = page.getTotalElements();

        return PageRP.builder()
                .content(list)
                .total(total)
                .build();
    }

    @Override
    public User resetPassword(String username) {

        User user = userRepository.findByUserName(username);
        if (user == null) throw new GeneralException(Constant.RESPONSE.CODE.C404,
                Constant.RESPONSE.MESSAGE.C404_USER_EXISTS);

        user.setPassword(passwordEncoder.encode(defaultPassword));

        return userRepository.save(user);
    }

    @Override
    public ActionRP uploadAvatar(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            throw new GeneralException(Constant.RESPONSE.CODE.ERROR, Constant.COMMON.SERVER_NOT_RECEIVED);
        }

        String extention = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        String pathLocation = locationRepository.findByName(applicationProperties.getAvatar()).getAddress() + File.separator + SecurityUtil.getCurrentUserId() + File.separator;

        File fileDir = new File(pathLocation);
        if (!fileDir.exists()) {
            FileUtil.createDir(pathLocation);
        }
        String encodeName = FileUtil.genarateName();
        String fileName = encodeName + "." + extention;
        File file = new File(pathLocation + fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * enbable old avatar
         */
        this.unableOldAvatar();

        UserAvatar avatar = UserAvatar.builder()
                .userID(SecurityUtil.getCurrentUserId())
                .originName(multipartFile.getOriginalFilename())
                .currentName(fileName)
                .folder(pathLocation)
                .build();

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, avatarRepository.save(avatar).getId())
                .build());
    }

    public void unableOldAvatar() {

        Long userId = SecurityUtil.getCurrentUserId();
        avatarRepository.unableOldAvatar(userId);
    }

    @Override
    public byte[] findAvatar(Long userId) {

        UserAvatar avatar = avatarRepository.findOneByID(userId);
        if (avatar != null) {
            Path path = Paths.get(avatar.getFolder(), avatar.getCurrentName());
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void setAccessToken(String username, String accessToken, String refreshToken) {

        User user = userRepository.findByUserName(username);

        if (user == null) throw new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.C404_USER);

        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Override
    public ActionRP findSelfProfile(Long userId) {

        User user = userRepository.findByID(userId);

        List<RoleRP> roleRPs = roleMapper.toRoleRPFromRoles(roleRepository.findByUserId(userId));

        List<MenuItemRP> menuItemRPs = menuItemService.findMenuItemByUserId(userId).toList();

        SelfProfile selfProfile = SelfProfile.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .isActive(user.getIsActive())
                .accessToken(user.getAccessToken())
                .menuItems(menuItemRPs)
                .roles(roleRPs)
                .provinceId(user.getProvinceId())
                .districtId(user.getDistrictId())
                .areaId(user.getAreaId())
                .info(user.getInfo())
                .typeUser(user.getTypeUser())
                .isCompany(checkCompany(user.getBussinessTypes()))
                .address(user.getAddress())
                .build();

        List<Object> wrap = new ArrayList<>();
        wrap.add(selfProfile);

        return new ActionRP(ImmutableMap.builder()
                .put("PO_RS", wrap)
                .build());
    }

    private long checkCompany(List<BussinessType> userBussiness){
        for(BussinessType b: userBussiness) if (b.getId() == 1L) return 1;
        return 0;
    }

    @Override
    public PageRP findById(Long id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_USER)
        );

        UserRP userRP = userMapper.toUserRpFromUser(user);

        return PageRP.builder()
                .total(1L)
                .content(userRP)
                .build();
    }

    @Override
    public ActionRP updateUser(UserUpdateRQ userUpdateRQ) {

        User oldUser = userRepository.findById(userUpdateRQ.getId()).orElseThrow(() ->
                new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.C404_USER)
        );

        User user = userMapper.toUserFromUserUpdateRQ(userUpdateRQ);
        user.setId(userUpdateRQ.getId());

        List<Role> roles = new ArrayList<>();
        userUpdateRQ.getRoles().forEach(role ->
                roles.add(roleRepository.findById(role).get())
        );
        user.setRoles(roles);
        user.setPassword(oldUser.getPassword());

        User userUpdate = userRepository.save(user);

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, userUpdate.getId())
                .build());
    }

    @Override
    public ActionRP changePassword(UserChangePasswordRQ userChangePasswordRQ) {

        User user = userRepository.findById(userChangePasswordRQ.getId()).orElseThrow(
                () -> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_USER_EXISTS)
        );
        String pass = passwordEncoder.encode(userChangePasswordRQ.getPassword());
        user.setPassword(pass);

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, userRepository.save(user).getId())
                .build());
    }

    @Override
    public ActionRP checkUserNamePassword(UserLoginRQ userLoginRQ) {

        User user = userRepository.findByUserName(userLoginRQ.getUsername());

        if (user == null)
            throw new GeneralException(Constant.RESPONSE.CODE.ERROR, Constant.RESPONSE.MESSAGE.ACCOUNT_NOT_EXISTS);


        if (user.getPassword() != passwordEncoder.encode(userLoginRQ.getPassword()))
            throw new GeneralException(Constant.RESPONSE.CODE.ERROR, Constant.RESPONSE.MESSAGE.PASSWORD_WRONG);

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, user.getId())
                .build());
    }


    @Override
    public ActionRP deleteById(DeleteRQ deleteRQ) {

        User user = userRepository.findById(deleteRQ.getId()).orElseThrow(
                () -> new NotFoundException(Constant.RESPONSE.MESSAGE.C404_USER)
        );
        user.setIsActive(Boolean.FALSE);
        user.setIsAccountNonExpired(Boolean.FALSE);
        user.setIsCredentialsNonExpired(Boolean.FALSE);
        user.setIsAccountNonLocked(Boolean.FALSE);
        user.setIsEnabled(Boolean.FALSE);

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, userRepository.save(user).getId())
                .build());
    }

    private String getRoleId(List<Role> roleList) {

        StringBuilder str = new StringBuilder();
        for (Role role : roleList) {
            str.append(role.getId() + "|");
        }
        if (roleList.size() > 1){
            return (str.toString()).substring(0,str.length()-1);
        }
        return str.toString();
    }

    private String getBussinessId(List<BussinessType> list) {

        StringBuilder str = new StringBuilder();
        for (BussinessType b : list) str.append(b.getId() + "|" );

        if (list.size()>1) {
            return (str.toString()).substring(0, str.length() - 1);
        }

        return str.toString();
    }

    private Long getLogDiary(List<BussinessType> list) {

        boolean check = false;

        for (BussinessType b : list) if (b.getIsLogDiary() == true) check = true;

        if (check) return 1L;
        else return 0L;
    }

    private List<BussinessType> getBussinessType(List<Long> ids) {

        return bussinessTypeRepository.findByIds(ids);
    }


}

