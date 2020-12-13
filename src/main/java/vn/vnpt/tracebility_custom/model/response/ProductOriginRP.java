package vn.vnpt.tracebility_custom.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOriginRP {

    @JsonProperty("N_ID")
    private Long id;

    @JsonProperty("S_NAME")
    private String name;

    @JsonProperty("N_PRODUCT_TYPE")
    private Long productTypeId;

}
