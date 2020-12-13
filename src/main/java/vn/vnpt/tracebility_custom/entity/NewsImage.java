package vn.vnpt.tracebility_custom.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsImage extends BaseId {

    private String name;

    private String fullname;

    private String originName;

    private Long sizeFile;

    private String keyUpload;

    private boolean isActive;

    private Long newsId;
}
