package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select  u from User u where u.username = :username and u.isActive =true")
    User findByUserName(String username);

    @Query("select  u from User u where u.username = :username or u.phone = :phone or u.email= :email")
    User findByUserNameOrPhoneOrEmail(String username, String phone, String email);

    @Query("select u from User u where u.id = :id and u.isActive =true")
    User findByID(Long id);

    @Query("select u from User u where (:keyword is null " +
            "or u.fullname like %:keyword% " +
            "or u.email like %:keyword% " +
            "or u.username like %:keyword% " +
            "or u.phone like %:keyword% )" +
            "and (:status is null or u.isActive = :status ) ")
    Page<User> findByMultiProperties(Pageable pageable,
                                     String keyword,
                                     Boolean status);

}
