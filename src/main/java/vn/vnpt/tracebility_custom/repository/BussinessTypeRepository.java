package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.BussinessType;

import java.util.List;

public interface BussinessTypeRepository extends JpaRepository<BussinessType, Long> {

    @Query("select b from BussinessType  b where b.id in :ids")
    List<BussinessType> findByIds(List<Long> ids);
}
