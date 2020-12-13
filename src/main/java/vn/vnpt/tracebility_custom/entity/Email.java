package vn.vnpt.tracebility_custom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Email extends BaseModel {

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String subject;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "email", cascade = CascadeType.PERSIST)
    private List<FileAttack> fileAttacks;

    @NotNull
    @ManyToMany()
    private List<User> userRecievers;

    @Builder.Default
    private Boolean isSended = Boolean.FALSE;

    private LocalDate sendDate;
}
