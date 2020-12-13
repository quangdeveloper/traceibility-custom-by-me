package vn.vnpt.tracebility_custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.CommonService;
import vn.vnpt.tracebility_custom.util.Constant;


@RestController
@RequestMapping(value = "/common", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@SessionAttributes({"keyUpload"})
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/nonAuthen/upload")
    public ResponseEntity<Response> uploadFile(@ModelAttribute("keyUpload") String key,
                                           @RequestParam("file") MultipartFile[] uploadFile) {
        return ResponseEntity.ok().body(
                Response.builder()
                        .map(commonService.uploadFile(key, uploadFile))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

}
