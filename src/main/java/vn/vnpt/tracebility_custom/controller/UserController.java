package vn.vnpt.tracebility_custom.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.tracebility_custom.entity.UserAvatar;
import vn.vnpt.tracebility_custom.model.param.PageParam;
import vn.vnpt.tracebility_custom.model.param.UserParam;
import vn.vnpt.tracebility_custom.model.request.*;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.UserService;
import vn.vnpt.tracebility_custom.util.Constant;
import vn.vnpt.tracebility_custom.util.SecurityUtil;


import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Response> findAll(@ApiParam @Valid PageParam p) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.findAll(p))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

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

    @GetMapping("/search")
    public ResponseEntity<Response> findByProperties(@ApiParam @Valid UserParam getListUserParam) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.findByMultiProperties(getListUserParam))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createUserForAdmin(@RequestBody UserNewRQ userNewRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.createUserForAdmin(userNewRQ))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<Response> uploadAvatar(@RequestParam("file") MultipartFile uploadFile) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.uploadAvatar(uploadFile))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] findAvatar(@RequestParam("avatar") Long userId) {

        return userService.findAvatar(userId);
    }

    @PostMapping("/getSelfProfile")
    public ResponseEntity<Response> findSefltProfile() {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.findSelfProfile(SecurityUtil.getCurrentUserId()))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @GetMapping(value = "/find-by-id")
    public ResponseEntity<Object> findById(@RequestParam("id") Long userId) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.findById(userId))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateUserForAdmin(@RequestBody UserUpdateRQ userUpdateRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.updateUser(userUpdateRQ))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }
    @PostMapping("/change-password")
    public ResponseEntity<Response> changePassword(@RequestBody UserChangePasswordRQ userChangePassword) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.changePassword(userChangePassword))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteForAdmin(@RequestBody DeleteRQ deleteRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.deleteById(deleteRQ))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Response> signUp(@RequestBody @Valid UserSignUpRQ u) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(userService.register(u))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }


    @GetMapping( value = "/getAvatar", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] findAvatar(@RequestParam("userId") String userId) {

        return userService.findAvatar(Long.parseLong(userId));
    }



}
