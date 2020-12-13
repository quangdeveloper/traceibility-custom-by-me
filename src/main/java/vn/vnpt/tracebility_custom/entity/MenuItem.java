package vn.vnpt.tracebility_custom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MenuItem extends BaseModel{

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String path;

    private Long parentId;

    private String icon;

    @ManyToMany(mappedBy = "menuItems")
    private List<Role> roles;

    private Long level;
}
