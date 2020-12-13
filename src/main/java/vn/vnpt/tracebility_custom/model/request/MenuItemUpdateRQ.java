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
public class MenuItemUpdateRQ {

    private Long id;

    @JsonProperty("title")
    private String name;

    private String description;

    @JsonProperty("link")
    private String path;

    @Builder.Default
    private Long parentId  = 0L;

    private String icon;

    private Long level;
}
