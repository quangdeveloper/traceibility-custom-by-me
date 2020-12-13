package vn.vnpt.tracebility_custom.model.param;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageParam {

    @NotNull
    @Min(1)
    private Integer pageNo;

    @NotNull
    @Min(5)
    private Integer pageSize;


    private String partner;

    private String signature;

}

