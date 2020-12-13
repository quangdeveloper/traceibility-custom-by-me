package vn.vnpt.tracebility_custom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import vn.vnpt.tracebility_custom.entity.Product;
import vn.vnpt.tracebility_custom.model.response.ProductRP;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "product.productType.id",target = "productTypeId"),
            @Mapping(source = "product.user.fullname", target ="creator")
    })
    ProductRP toProductRpFromProduct(Product product);
}
