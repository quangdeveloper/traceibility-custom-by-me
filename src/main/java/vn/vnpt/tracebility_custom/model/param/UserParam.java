package vn.vnpt.tracebility_custom.model.param;

import lombok.Data;

@Data
public class UserParam extends PageParam {

    private String keyword;

    private Boolean status;
}
