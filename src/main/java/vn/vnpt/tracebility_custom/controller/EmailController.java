package vn.vnpt.tracebility_custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.tracebility_custom.model.request.EmailNewRQ;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.service.EmailService;
import vn.vnpt.tracebility_custom.util.Constant;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<Response> createEmail(@ModelAttribute @Valid EmailNewRQ emailNewRQ) {

        return ResponseEntity.ok().body(
                Response.builder()
                        .map(emailService.createEmail(emailNewRQ))
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }

    @GetMapping("/send")
    public ResponseEntity<Response> sendEmail() {

        emailService.autoSendListEmail();
        return ResponseEntity.ok().body(
                Response.builder()
                        .map("sended")
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .build()
        );
    }
}
