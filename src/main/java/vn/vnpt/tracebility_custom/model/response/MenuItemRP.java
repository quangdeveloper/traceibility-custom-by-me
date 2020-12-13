package vn.vnpt.tracebility_custom.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MenuItemRP {

    private Long id;

    @JsonProperty("title")
    private String name;

    private String label;

    private String key;

    private String description;

    @JsonProperty("link")
    private String path;

    private Long parentId;

    private String parentName;

    private String icon;

    private Long level;

    private List<MenuItemRP> children;
}
