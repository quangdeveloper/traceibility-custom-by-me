package vn.vnpt.tracebility_custom.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailNewRQ {

    @NotNull
    private String subject;

    @NotNull
    private String content;

    @ToString.Exclude
    private MultipartFile[] fileAttacks;

    @NotNull
    private List<Long> recievers;

}
