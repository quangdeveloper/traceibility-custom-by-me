package vn.vnpt.tracebility_custom.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemNewRQ {

    @JsonProperty("title")
    private String name;

    private String description;

    @JsonProperty("link")
    private String path;

    private Long parentId;

    private String icon;

    private Long level;
}
