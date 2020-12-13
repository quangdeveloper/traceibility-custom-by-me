package vn.vnpt.tracebility_custom.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductParam extends PageParam {

    private String keyword;

    private Long companyId;

    private Long isCompanyFilter;

    @JsonProperty("originID")
    private Long originId;

    private Boolean status;

    @JsonProperty("typeID")
    private Long typeId;
}
