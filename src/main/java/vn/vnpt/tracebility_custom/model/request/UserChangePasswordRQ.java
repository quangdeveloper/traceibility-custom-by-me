package vn.vnpt.tracebility_custom.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChangePasswordRQ {

    @NotNull
    private Long id;

    @NotNull
    private String password;

}
