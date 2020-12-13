package vn.vnpt.tracebility_custom.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.tracebility_custom.model.param.RoleParam;
import vn.vnpt.tracebility_custom.model.request.RoleNewRQ;
import vn.vnpt.tracebility_custom.model.request.RoleUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.RoleService;
import vn.vnpt.tracebility_custom.util.Constant;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleService;


    @GetMapping("")
    public ResponseEntity<Response> findAllRole() {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(roleService.findlAll())
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Response> findByProperties(@ApiParam @Valid RoleParam getListRoleParam) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(roleService.findByProperties(getListRoleParam))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @GetMapping("/user")
    public ResponseEntity<Response> findByUserID(@RequestParam Long id) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(roleService.findByUserID(id))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }


    @GetMapping("/find-by-id")
    public ResponseEntity<Response> findByID(@RequestParam Long id) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(roleService.findById(id))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createRole(@RequestBody @Valid RoleNewRQ roleNewRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(roleService.createRole(roleNewRQ))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateRole(@RequestBody @Valid RoleUpdateRQ roleUpdateRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(roleService.updateRole(roleUpdateRQ))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }


    @GetMapping("/view-user")
    public ResponseEntity<Response> viewUser(@RequestParam Long id){

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(roleService.viewUser(id))
                        .build()
        );
    }


}
