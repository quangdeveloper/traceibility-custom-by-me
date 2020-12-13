package vn.vnpt.tracebility_custom.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vnpt.tracebility_custom.entity.User;
import vn.vnpt.tracebility_custom.exception.GeneralException;
import vn.vnpt.tracebility_custom.repository.UserRepository;
import vn.vnpt.tracebility_custom.util.Constant;


@Slf4j
@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Lấy ra 1 userDetail thông qua tài username khi đăng nhập của người dùng
     * <p>
     * hỏi anh xem tai sao yêu cầu lấy ra user detail nhưng hàm lại trả về userPrincipal
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new GeneralException(Constant.RESPONSE.CODE.C404,
                    Constant.RESPONSE.MESSAGE.C404_USER + " với id: " + user.getId());
        }

        return new UserPrincipal(user);
    }

    /**
     * Tìm user thông qua id cửa user
     */

    public UserDetails loadUserById(Long id) {


        User user = userRepository.findById(id).orElseThrow(
                () -> new GeneralException(Constant.RESPONSE.CODE.C404,
                        Constant.RESPONSE.MESSAGE.C404_USER + " với id: " + id)
        );

        return new UserPrincipal(user);
    }
}
