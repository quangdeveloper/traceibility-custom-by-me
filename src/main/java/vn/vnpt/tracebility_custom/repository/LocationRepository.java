package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.Location;

public interface LocationRepository extends JpaRepository<Location,Long> {

    @Query("select l from Location l where l.name = :name")
    Location findByName(String name);
}
