package vn.vnpt.tracebility_custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.BussinessTypeService;
import vn.vnpt.tracebility_custom.util.Constant;

@RestController
@RequestMapping("/businessType")
public class BussinessTypeController {

    @Autowired
    private BussinessTypeService bussinessTypeService;

    @GetMapping("/getCbb")
    public ResponseEntity<Response> findAll() {
        return ResponseEntity.ok().body(
                Response.builder()
                        .map(bussinessTypeService.findAll())
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

}
