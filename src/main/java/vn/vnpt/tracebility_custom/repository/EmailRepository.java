package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query("select m  from Email m where m.isSended = false")
    Page<Email> findEmailWithIsSendFalse(Pageable pageable);
}
