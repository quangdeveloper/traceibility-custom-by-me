package vn.vnpt.tracebility_custom.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpRQ extends WebBase {

    @NotNull
    @JsonProperty("userID")
    private String username;

    @NotNull
    @JsonProperty("name")
    private String fullname;

    @NotNull
    @JsonProperty("phone")
    private String phone;

    @NotNull
    @JsonProperty("email")
    private String email;

    private String address;

    @NotNull
    @JsonProperty("districtID")
    private String districtId;

    @NotNull
    @JsonProperty("provinceID")
    private String provinceId;


    @JsonProperty("moreInfo")
    private String info;

    @JsonProperty("wardID")
    private String areaId;

    @NotNull
    private List<Long> types;

}
