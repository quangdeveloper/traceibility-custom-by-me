package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.ProductType;

public interface ProductTypeRepository  extends JpaRepository<ProductType, Long> {

    @Query("select p from ProductType p where p.id = :id")
    ProductType findByID(Long id);
}
