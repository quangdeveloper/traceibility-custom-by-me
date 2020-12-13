package vn.vnpt.tracebility_custom.service;

import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.tracebility_custom.entity.User;
import vn.vnpt.tracebility_custom.model.param.PageParam;
import vn.vnpt.tracebility_custom.model.param.UserParam;
import vn.vnpt.tracebility_custom.model.request.*;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.model.response.PageRP;
import vn.vnpt.tracebility_custom.model.response.UserLoginRP;


public interface UserService {

    UserLoginRP login(UserLoginRQ user);

    User findByUsername(String username);

    ActionRP register(UserSignUpRQ userSignUpRQ);

    ActionRP createUserForAdmin(UserNewRQ userNewRQ);

    PageRP findAll(PageParam pageParam);

    PageRP findByMultiProperties(UserParam getListUserParam);

    User resetPassword(String username);

    ActionRP uploadAvatar(MultipartFile multipartFile);

    byte[] findAvatar(Long userId);

    void setAccessToken(String username, String accessToken, String refreshToken);

    ActionRP findSelfProfile(Long userId);

    PageRP findById(Long id);

    ActionRP updateUser(UserUpdateRQ userUpdateRQ);

    ActionRP changePassword(UserChangePasswordRQ userChangePasswordRQ);

    ActionRP checkUserNamePassword(UserLoginRQ userLoginRQ);

    ActionRP deleteById(DeleteRQ deleteRQ);


}
