package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.vnpt.tracebility_custom.entity.Product;
import vn.vnpt.tracebility_custom.entity.ProductType;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where (:companyId is null or p.createdByUserID = :companyId) " +
            "and (:status is null or p.isActive = :status) " +
            "and (:originId is null or p.originId = :originId) " +
            "and (:productType is null or p.productType = :productType) " +
            "and (:keyword is null or p.name like %:keyword%)" +
            "and (:isFilterCompany is null or (:isFilterCompany = 1L and p.id = p.originId) " +
            "or (:isFilterCompany = 2L and p.id <> p.originId))")
    Page<Product> findByProperties(String keyword,
                                   Boolean status,
                                   Long companyId,
                                   ProductType productType,
                                   Long originId,//san pham goc
                                   Long isFilterCompany,
                                   Pageable pageable);


    @Query("select p from Product p where p.isActive = true " +
            "and p.id = p.originId")
    List<Product> findProductOrigin();
}
