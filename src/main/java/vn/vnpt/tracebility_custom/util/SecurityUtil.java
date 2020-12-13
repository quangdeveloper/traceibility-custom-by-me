package vn.vnpt.tracebility_custom.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.vnpt.tracebility_custom.entity.UserAuditModel;
import vn.vnpt.tracebility_custom.security.UserPrincipal;


public class SecurityUtil {

    public SecurityUtil() {
    }

    /**
     * Printcipal co thể là 1 người, 1 hệ thống, 1 thiết bị thục hiện 1 tác vụ nào đó trong hệ thống
     * lay ra user đang đăng nhập vào hệ thống
     * khi user đang nhập vào hệ thống sẽ xuất hiện 1 Principal trong hệ thống
     * dựa SecurityContextHolder  để lấy ra User principal
     *
     * getContext: lấy ra bối cảnh trong hệ thống lúc truy xuất
     * */
    public static Long getCurrentUserId() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null){

            final  Object o = authentication.getPrincipal();

            if (o instanceof UserPrincipal){
                UserPrincipal userPrincipal = (UserPrincipal) o;
                return userPrincipal.getId();
            }
        }
        return 0L;
    }

    /**
     * tương như hàm trên nhưng hàm này tao ra 1 UserAuditModel
     *
     */

    public static UserAuditModel getUserAuditModel() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            final Object principal = authentication.getPrincipal();

            if (principal instanceof UserPrincipal) {

                UserPrincipal userPrincipal = (UserPrincipal) principal;

                return UserAuditModel.builder()
                        .id(userPrincipal.getId())
                        .username(userPrincipal.getUsername())
                        .build();
            }
        }
        return new UserAuditModel();
    }





}
