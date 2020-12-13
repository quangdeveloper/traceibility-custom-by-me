package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.MenuItem;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("select m from MenuItem m where m.id = :id")
    MenuItem findByID(Long id);

    @Query(value = "select mi.* " +
            "from menu_item mi  " +
            "where mi.id  in (select mir.menu_items_id from role_menu_items mir where mir.roles_id in (" +
            "select ur.roles_id " +
            "from user_roles ur " +
            "where ur.users_id = :userId)" +
            ")", nativeQuery = true)
    List<MenuItem> findMenuItemByUserId(Long userId);

    @Query("select m from MenuItem m where (:keyword is null or m.name like %:keyword%)")
    Page<MenuItem> findByProperties(String keyword, Pageable pageable);



}
