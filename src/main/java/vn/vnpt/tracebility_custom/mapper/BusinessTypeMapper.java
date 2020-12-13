package vn.vnpt.tracebility_custom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import vn.vnpt.tracebility_custom.entity.BussinessType;
import vn.vnpt.tracebility_custom.model.response.BusinessTypeRP;

@Mapper(componentModel = "spring")
public interface BusinessTypeMapper {

    @Mappings({})
    BusinessTypeRP toBusinessTypeRPFromBT(BussinessType bussinessType);
}
