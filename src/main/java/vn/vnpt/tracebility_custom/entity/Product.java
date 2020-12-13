package vn.vnpt.tracebility_custom.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseModel{

    @NotNull
    private String name;

    @NotNull
    private Long originId;

    @Column(columnDefinition = "TEXT")
    private String info;

    private String barcode;

    @ManyToOne
    @ToString.Exclude
    private ProductType productType;

    @ManyToOne
    @ToString.Exclude
    private User user;
}
