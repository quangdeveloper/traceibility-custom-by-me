package vn.vnpt.tracebility_custom.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserLoginRP {

    @JsonProperty("PO_ID")
    private Long id;

    @JsonProperty("PO_FULL_NAME")
    private String fullname;

    @JsonProperty("PO_TOKEN")
    private String jwt;

    @JsonProperty("PO_MENU")
    private List<MenuItemRP> menu;

    @JsonProperty("PO_ROLE")
    private String roles;

    @JsonProperty("PO_TYPE_USER")
    private String typeUser;

    @JsonProperty("PO_IS_LOG_DIARY")
    private Long isLogDiary;

    @JsonProperty("PO_BUSSINESS_TYPE")
    private String bussinessType;

    @JsonProperty("PO_AVATAR")
    private String avatarPath;
}
