package vn.vnpt.tracebility_custom.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role extends BaseModel {

    @NotNull
    private String code;

    @NotNull
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private Long parentId;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private List<User> users;

    @ManyToMany
    @ToString.Exclude
    private List<MenuItem> menuItems;

    @NotNull
    private Long level;

    @NotNull
    private Long orderDisplay;

    private String roleType;

    @Builder.Default
    private Boolean isCustomer = Boolean.FALSE;

    @Builder.Default
    private Boolean isAdmin = Boolean.TRUE;

}

