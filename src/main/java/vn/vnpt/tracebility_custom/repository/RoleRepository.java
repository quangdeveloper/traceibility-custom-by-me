package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByCode(String code);

    @Query(value = "select r.* " +
            "from role r  " +
            "where r.id in (" +
            "select distinct ur.roles_id " +
            "from user_roles ur " +
            "where ur.users_id = 1 " +
            ")", nativeQuery = true)
    List<Role> findByUserId(Long userId);

    @Query("select r from Role r where (:status is null or r.isActive = :status) " +
            "and (:keyword is null or r.name like %:keyword%)")
    Page<Role> findByProperties(String keyword,
                                Boolean status,
                                Pageable pageable);

    @Query("select r from Role r where r.id = :id")
    Role findByID(Long id);

}
