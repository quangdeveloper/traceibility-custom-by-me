package vn.vnpt.tracebility_custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.tracebility_custom.model.request.PnsRequest;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.FCMService;
import vn.vnpt.tracebility_custom.util.Constant;

@RestController
public class NotificationController {

    @Autowired
    private FCMService fcmService;

    @PostMapping("/")
    public ResponseEntity<Response> createNotification(@RequestBody PnsRequest pnsRequest){

        return ResponseEntity.ok(
                Response.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .map(fcmService.pushNotification(pnsRequest))
                        .build()
        );
    }

}
