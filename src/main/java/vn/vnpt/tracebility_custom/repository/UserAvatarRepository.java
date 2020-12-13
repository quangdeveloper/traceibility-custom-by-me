package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.UserAvatar;

import javax.transaction.Transactional;

@Transactional
public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {

    @Modifying
    @Query(value = "update user_avatar u set u.is_active = FALSE where u.userid = :id and u.is_active = true", nativeQuery = true)
    void unableOldAvatar(Long id);


    @Query(value = "select u from UserAvatar u where u.userID = :userId and u.isActive = true ")
    UserAvatar findOneByID(Long userId);

}
