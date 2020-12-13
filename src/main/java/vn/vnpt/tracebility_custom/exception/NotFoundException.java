package vn.vnpt.tracebility_custom.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotFoundException extends RuntimeException {

    @Builder.Default
    private String code = "C404";

    private String message;

    public NotFoundException(String mess){
        this.message= mess;
    }
}
