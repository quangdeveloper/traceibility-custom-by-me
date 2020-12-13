package vn.vnpt.tracebility_custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.tracebility_custom.entity.Role;
import vn.vnpt.tracebility_custom.model.request.*;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.MenuItemService;
import vn.vnpt.tracebility_custom.service.UserService;
import vn.vnpt.tracebility_custom.util.Constant;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid UserLoginRQ user) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(userService.login(user))
                        .build()
        );
    }


    @PostMapping("/get-self-profile")
    public ResponseEntity<Response> findSelfProfile(@RequestBody UserProfileRQ userProfileRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.findSelfProfile(userProfileRQ.getUserId()).getValue())
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/get-menu")
    public ResponseEntity<Response> findMenu(@RequestBody UserProfileRQ userProfileRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(menuItemService.findMenuItemByUserId(userProfileRQ.getUserId()))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }


}
