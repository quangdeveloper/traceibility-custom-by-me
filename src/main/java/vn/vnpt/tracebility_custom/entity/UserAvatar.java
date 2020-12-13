package vn.vnpt.tracebility_custom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAvatar extends BaseModel{

    @NotNull
    private Long userID;

    /**
     * Name on window client
     */
    @NotNull
    private String originName;

    /**
     * Name after encode , store in server
     */
    @NotNull
    private String currentName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private String folder;

    @Builder.Default
    private Boolean status = Boolean.TRUE;

}
