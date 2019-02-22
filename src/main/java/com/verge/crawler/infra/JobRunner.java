package com.verge.crawler.infra;

import com.verge.crawler.crawler.PageRetriever;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class JobRunner {

    @PostConstruct
    private void runJob() throws SchedulerException {
        JobDetail job = getJobDetail();
        Trigger trigger = getTrigger(30);

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

    private JobDetail getJobDetail() {
        return JobBuilder.newJob(PageRetriever.class)
                .withIdentity("Sony crawler")
                .build();
    }

    private Trigger getTrigger(int seconds) {
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger")
                .withSchedule(
                    SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInSeconds(seconds)
                            .repeatForever())
                .build();
    }
}
