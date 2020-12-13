package vn.vnpt.tracebility_custom.service;


import vn.vnpt.tracebility_custom.entity.Email;
import vn.vnpt.tracebility_custom.entity.FileAttack;
import vn.vnpt.tracebility_custom.entity.User;
import vn.vnpt.tracebility_custom.model.request.EmailNewRQ;
import vn.vnpt.tracebility_custom.model.response.ActionRP;

import java.util.List;

public interface EmailService {

    ActionRP createEmail(EmailNewRQ emailNewRQ);

    void sendMimeEmail(String subject, String content, String toAddressMail, List<FileAttack> list);

    void autoSendMailToListUser(Email email, List<User> userList);

    void autoSendListEmail();

    void changeIsSendAndSendDateEmail(Long emailId);
}
