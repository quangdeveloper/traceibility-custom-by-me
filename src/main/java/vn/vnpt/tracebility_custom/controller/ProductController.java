package vn.vnpt.tracebility_custom.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.tracebility_custom.model.param.ProductParam;
import vn.vnpt.tracebility_custom.model.request.ProductNewRQ;
import vn.vnpt.tracebility_custom.model.request.ProductUpdateRQ;
import vn.vnpt.tracebility_custom.model.request.RoleNewRQ;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.ProductService;
import vn.vnpt.tracebility_custom.util.Constant;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/getList")
    public ResponseEntity<Response> findByProperties(@ApiParam @Valid ProductParam productParam) {

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(productService.findByProperties(productParam))
                        .build()
        );
    }

    @GetMapping("/cbbOrigin")
    public ResponseEntity<Response> findProductOrigin() {

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(productService.findProductOrigin())
                        .build()
        );
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Response> createProduct(@RequestBody @Valid ProductNewRQ productNewRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(productService.createProduct(productNewRQ))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @PostMapping("/editProduct")
    public ResponseEntity<Response> editProduct(@RequestBody @Valid ProductUpdateRQ productUpdateRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(productService.updateProduct(productUpdateRQ))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

}
