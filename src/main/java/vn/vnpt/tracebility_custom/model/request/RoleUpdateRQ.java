package vn.vnpt.tracebility_custom.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleUpdateRQ {

    private Long id;

    private String code;

    private String name;

    private String description;

    private Long parentId;

    private Long level;

    @JsonProperty("menus")
    private List<Long> menuIds;

}
