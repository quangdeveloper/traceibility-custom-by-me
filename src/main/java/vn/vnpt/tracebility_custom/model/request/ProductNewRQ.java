package vn.vnpt.tracebility_custom.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductNewRQ extends WebBase{

    private String productName;

    private String info;

    private Long originID;

    private String productBarcode;

    private Long typeID;

    private Boolean status;

    private List<Long> removeImg;
}
