package vn.vnpt.tracebility_custom.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRP {

    private Long id;

    private String username;

    private String fullname;

    private String email;

    private String phone;

    private Boolean isActive;
}
