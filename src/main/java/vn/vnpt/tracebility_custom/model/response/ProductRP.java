package vn.vnpt.tracebility_custom.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRP {

    @JsonProperty("N_ID")
    private Long id;

    @JsonProperty("S_NAME")
    private String name;

    @JsonProperty("N_ORIGIN_ID")
    private Long originId;

    @JsonProperty("S_INFO")
    private String info;

    @JsonProperty("S_BARCODE")
    private String barcode;

    @JsonProperty("N_PRODUCT_TYPE")
    private Long productTypeId;

    @JsonProperty("S_CREATOR")
    private Long creator;
}
