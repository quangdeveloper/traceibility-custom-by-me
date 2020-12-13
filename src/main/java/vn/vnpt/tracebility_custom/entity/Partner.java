package vn.vnpt.tracebility_custom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner extends BaseId{

    @NotNull
    private String shortname;

    @NotNull
    private String partnerName;

    @NotNull
    private String privateKey;

    @NotNull
    private String publicKey;

    @NotNull
    private String status;
}
