package vn.vnpt.tracebility_custom.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

import vn.vnpt.tracebility_custom.model.request.PnsRequest;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.service.FCMService;
import vn.vnpt.tracebility_custom.util.Constant;

import javax.transaction.Transactional;

@Transactional
@Service
public class FCMServiceImpl implements FCMService {

    @Override
    public ActionRP pushNotification(PnsRequest pnsRequest) {

        Message message = Message.builder()
                .putData("content", pnsRequest.getContent())
                .setToken(pnsRequest.getScmToken())
                .build();

        String response = null;

        try {

            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {

            e.printStackTrace();
        }

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, response)
                .build());
    }

}
