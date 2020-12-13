package vn.vnpt.tracebility_custom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlideImage extends BaseId {

    @NotNull
    private Long slideId;

    @NotNull
    private String name;

    @NotNull
    private String fullname;

    @NotNull
    private String originName;

    @NotNull
    private Long sizeFile;

    @NotNull
    private String keyUpload;

    @NotNull
    @Builder.Default
    private Boolean isActive = Boolean.TRUE;
}
