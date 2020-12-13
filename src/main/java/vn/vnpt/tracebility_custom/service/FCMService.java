package vn.vnpt.tracebility_custom.service;

import vn.vnpt.tracebility_custom.model.request.PnsRequest;
import vn.vnpt.tracebility_custom.model.response.ActionRP;

public interface FCMService {

    ActionRP pushNotification(PnsRequest pnsRequest);

}
