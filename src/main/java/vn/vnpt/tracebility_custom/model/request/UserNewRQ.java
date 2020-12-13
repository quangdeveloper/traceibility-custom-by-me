package vn.vnpt.tracebility_custom.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNewRQ {

    private String username;

    private String password;

    private String fullname;

    private String email;

    private String phone;

    private List<Long> roles;
}
