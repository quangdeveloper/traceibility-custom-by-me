package vn.vnpt.tracebility_custom.model.request;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Data
@MappedSuperclass
public abstract class WebBase {

    @NotNull
    private String partner;

    @NotNull
    private String signature;
}
