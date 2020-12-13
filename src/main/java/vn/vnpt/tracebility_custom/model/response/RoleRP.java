package vn.vnpt.tracebility_custom.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoleRP {

    private Long id;

    private String key;

    private String label;

    private Long level;

    private List<RoleRP> children ;

    private Long parentId;

    private String code;

    private String name;

    private List<Long> menus;
}
