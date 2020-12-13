package vn.vnpt.tracebility_custom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductType extends BaseId{

    @NotNull
    private String name;

    @NotNull
    @Builder.Default
    private Boolean isActive = Boolean.TRUE;


    @OneToMany(mappedBy = "productType")
    private List<Product> product;
}
