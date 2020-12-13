package vn.vnpt.tracebility_custom.schedule;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import vn.vnpt.tracebility_custom.schedule.job.MailJob;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class ScheduleConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        System.out.println(" spring bean init");
    }

    @Bean()
    public SpringBeanJobFactory springBeanJobFactory() {

        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springBeanJobFactory());
        System.out.println(schedulerFactoryBean.getClass());
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail jobDetail, SchedulerFactoryBean factoryBean) throws SchedulerException {
//        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        Scheduler scheduler = factoryBean.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        return scheduler;
    }

    @Bean
    public JobDetail jobDetail() {
        return newJob()
                .ofType(MailJob.class)
                .storeDurably()
                .withIdentity(JobKey.jobKey("job_1"))
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        int frequencyInSec = 60;
        return newTrigger().forJob(job)
                .withIdentity(TriggerKey.triggerKey("trigger_1"))
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInSeconds(frequencyInSec)
                                .repeatForever())
                .build();
    }
}
