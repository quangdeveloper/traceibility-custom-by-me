package vn.vnpt.tracebility_custom.schedule.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.vnpt.tracebility_custom.service.EmailService;

@Component
@Slf4j

public class MailJob implements Job {

    @Autowired
    private EmailService mailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        mailService.autoSendListEmail();
    }
}
