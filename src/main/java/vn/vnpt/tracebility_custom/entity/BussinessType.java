package vn.vnpt.tracebility_custom.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BussinessType extends BaseModel{

    @NotNull
    private String name;

    @NotNull
    @Builder.Default
    private Boolean isLogDiary = Boolean.FALSE;

    @NotNull
    @Builder.Default
    private Boolean isDefault= Boolean.FALSE;

    @NotNull
    private Long status;

    @ManyToMany(mappedBy = "bussinessTypes")
    @ToString.Exclude
    private List<User> users;


}
