package vn.vnpt.tracebility_custom.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage extends BaseId {

    @NotNull
    private String name;

    @NotNull
    private String fullname;

    @NotNull
    private String originName;

    @NotNull
    private Long size;

    @NotNull
    private String keyGen;

    @NotNull
    @Builder.Default
    private Boolean isActive = Boolean.TRUE;

}
