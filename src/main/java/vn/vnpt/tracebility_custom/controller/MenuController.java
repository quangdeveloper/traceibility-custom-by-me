package vn.vnpt.tracebility_custom.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.tracebility_custom.model.param.MenuParam;
import vn.vnpt.tracebility_custom.model.request.DeleteRQ;
import vn.vnpt.tracebility_custom.model.request.MenuItemNewRQ;
import vn.vnpt.tracebility_custom.model.request.MenuItemUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.MenuItemService;
import vn.vnpt.tracebility_custom.util.Constant;

import javax.validation.Valid;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/user")
    public ResponseEntity<Response> findMenuByUser(@RequestParam Long userId) {

        return ResponseEntity.ok(
                Response.builder()
                .code(Constant.RESPONSE.CODE.OK)
                .message(Constant.RESPONSE.MESSAGE.OK)
                .map(menuItemService.findMenuItemByUserId(userId))
                .build()
        );
    }


    @GetMapping("")
    public ResponseEntity<Response> findAll() {

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(menuItemService.findAll())
                        .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Response> findByProperties(@ApiParam @Valid MenuParam getListMenuParam) {

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(menuItemService.findByProperties(getListMenuParam))
                        .build()
        );
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<Response> findById(@RequestParam Long id) {

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(menuItemService.findById(id))
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createMenuItem(@RequestBody MenuItemNewRQ menuItemNewRQ){

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(menuItemService.createMenuItem(menuItemNewRQ))
                        .build()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateMenuItem(@RequestBody MenuItemUpdateRQ menuItemUpdateRQ){

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(menuItemService.updateMenuItem(menuItemUpdateRQ))
                        .build()
        );
    }

    @PostMapping("/delete")
    public ResponseEntity<Response> deleteMenuItem(@RequestBody DeleteRQ deleteRQ){

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(menuItemService.deleteById(deleteRQ.getId()))
                        .build()
        );
    }

    @GetMapping("/role")
    public ResponseEntity<Response> findMenuByRole(@RequestParam Long id){

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(menuItemService.findMenuByRole(id))
                        .build()
        );
    }



}
