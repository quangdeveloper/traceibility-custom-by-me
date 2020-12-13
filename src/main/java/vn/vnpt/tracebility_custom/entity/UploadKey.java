package vn.vnpt.tracebility_custom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadKey extends BaseId{

    @NotNull
    private String keyGen;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private LocalDate expiredDate;

    @NotNull
    private String uploadType;

    @NotNull
    private String status;

    @NotNull
    private Long val1;

    private Long val2;

    private Long val3;

    @NotNull
    private Long maxFile;

}
