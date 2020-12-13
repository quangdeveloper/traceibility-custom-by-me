package vn.vnpt.tracebility_custom.model.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelfProfile {

    @JsonProperty("N_ID")
    private Long id;

    @JsonProperty("S_USER_ID")
    private String username;

    @JsonProperty("S_FULL_NAME")
    private String fullname;

    @JsonProperty("S_TYPE_USER")
    private String typeUser;

    @JsonIgnore
    @JsonProperty("N_IS_ACTIVE")
    private Boolean isActive;

    @JsonProperty("S_EMAIL")
    private String email;

    @JsonProperty("s_PHONE")
    private String phone;

    @JsonProperty("S_ADDRESS")
    private String address;

    @JsonProperty("S_PROVINCE_ID")
    private String provinceId;

    @JsonProperty("S_DISTRICT_ID")
    private String districtId;

    @JsonProperty("S_AREA_ID")
    private String areaId;

    @JsonProperty("S_VAVATAR")
    private String avatar = "no need here";

    @JsonProperty("C_INFO")
    private String info;

    @JsonProperty("N_IS_COMPANY")
    private Long isCompany;

    @JsonIgnore
    private String accessToken;

    @JsonIgnore
    private List<MenuItemRP> menuItems;

    @JsonIgnore
    private List<RoleRP> roles;

}
