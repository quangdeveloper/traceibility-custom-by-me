package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.UploadKey;

public interface UploadKeyRepository extends JpaRepository<UploadKey, Long> {

    @Query("select k from UploadKey k where k.keyGen = :keyGen")
    UploadKey findByKey(String keyGen);

}
