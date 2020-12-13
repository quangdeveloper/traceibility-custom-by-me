package vn.vnpt.tracebility_custom.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String fullname;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @ManyToMany
    @ToString.Exclude
    private List<Role> roles;

    @ManyToMany(mappedBy = "userRecievers")
    private List<Email> emails;

    private String accessToken;

    private String refreshToken;

    @Builder.Default
    private Boolean isAccountNonExpired = Boolean.TRUE;

    @Builder.Default
    private Boolean isAccountNonLocked = Boolean.TRUE;

    @Builder.Default
    private Boolean isCredentialsNonExpired = Boolean.TRUE;

    @Builder.Default
    private Boolean isEnabled = Boolean.TRUE;

    @Builder.Default
    private Boolean isChangPassword= Boolean.FALSE;

    private String salt;

    @NotNull
    private String typeUser;

    @NotNull
    private String districtId;

    @NotNull
    private String provinceId;

    @NotNull
    private String areaId;

    private String info;

    private String tokenActive;

    @ManyToMany
    @ToString.Exclude
    private List<BussinessType> bussinessTypes;

    @NotNull
    @Builder.Default
    private String status = new String("WA");

    @Builder.Default
    @NotNull
    private Boolean isRegister = Boolean.TRUE;

    private String address;

    private String avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private List<Product> products;

}
