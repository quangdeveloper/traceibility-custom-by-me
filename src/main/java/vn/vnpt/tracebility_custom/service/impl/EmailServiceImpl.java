package vn.vnpt.tracebility_custom.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.vnpt.tracebility_custom.entity.Email;
import vn.vnpt.tracebility_custom.entity.FileAttack;
import vn.vnpt.tracebility_custom.entity.User;
import vn.vnpt.tracebility_custom.exception.GeneralException;
import vn.vnpt.tracebility_custom.model.request.EmailNewRQ;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.repository.EmailRepository;
import vn.vnpt.tracebility_custom.repository.UserRepository;
import vn.vnpt.tracebility_custom.service.EmailService;
import vn.vnpt.tracebility_custom.service.FileAttackUploadService;
import vn.vnpt.tracebility_custom.util.Constant;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    @Value("${config.mail.username}")
    private String addressMailFrom;

    @Value("${config.mail.properties.mail.amount}")
    private int amountMailSend;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private FileAttackUploadService fileAttackUploadService;

    @Override
    public ActionRP createEmail(EmailNewRQ emailNewRQ) {

        List<User> userList = checkExistsUser(emailNewRQ.getRecievers());

        Email email = new Email();
        email.setSubject(emailNewRQ.getSubject());
        email.setContent(emailNewRQ.getContent());
        email.setUserRecievers(userList);

        if (emailNewRQ.getFileAttacks() != null && emailNewRQ.getFileAttacks().length > 0) {

            List<FileAttack> fileAttackList =
                    fileAttackUploadService.uploadFiles(emailNewRQ.getFileAttacks(), email);
            email.setFileAttacks(fileAttackList);
        }

        return new ActionRP(ImmutableMap.builder()
                .put(Constant.RESPONSE.JSON_KEY.RETURN_VALUE, emailRepository.save(email).getId())
                .build());
    }


    @Override
    public void sendMimeEmail(String subject, String content, String toMail, List<FileAttack> listFile) {

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content);
            mimeMessageHelper.setFrom(addressMailFrom);
            mimeMessageHelper.setTo(toMail);

            for (FileAttack fileAttack : listFile) {

                FileSystemResource file = new FileSystemResource(
                        new File(fileAttack.getPath())
                );
                mimeMessageHelper.addAttachment(file.getFilename(), file);
            }

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void autoSendListEmail() {

        Pageable pageable = PageRequest.of(0, amountMailSend, Sort.by("id").descending());
        Page<Email> page = emailRepository.findEmailWithIsSendFalse(pageable);

        List<Email> emailList = page.getContent();

        for (Email email : emailList) {

            autoSendMailToListUser(email, email.getUserRecievers());
        }
    }

    @Override
    public void autoSendMailToListUser(Email email, List<User> userList) {

        changeIsSendAndSendDateEmail(email.getId());

        for (User user : userList) {

            sendMimeEmail(
                    email.getSubject(),
                    email.getContent(),
                    user.getEmail(),
                    email.getFileAttacks()
            );
        }
    }

    private List<User> checkExistsUser(List<Long> userIds) {

        List<User> userList = new ArrayList<>();

        for (Long userId : userIds) {

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.C404_USER)
            );

            userList.add(user);
        }
        return userList;
    }

    @Override
    public void changeIsSendAndSendDateEmail(Long emailId) {

        Email email = emailRepository.findById(emailId).orElseThrow(
                ()-> new GeneralException(Constant.RESPONSE.CODE.C404, "Not exists email with id. Are you sure it?")
        );

        email.setIsSended(true);
        email.setSendDate(LocalDate.now());

        emailRepository.save(email);
    }
}
