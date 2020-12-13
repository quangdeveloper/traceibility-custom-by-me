package vn.vnpt.tracebility_custom.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News extends BaseModel{

    private String title;

    private String content;

    private String description;

}
