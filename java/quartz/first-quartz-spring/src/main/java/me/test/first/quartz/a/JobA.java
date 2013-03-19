package me.test.first.quartz.a;

import java.util.HashMap;

import me.test.first.quartz.Util;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobA implements Job {

    private Logger log = LoggerFactory.getLogger(JobA.class);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        // TODO 开始日志

        StringBuilder buf = new StringBuilder();

        Scheduler scheduler = context.getScheduler();
        try {
            buf.append("\n  Scheduler = " + Util.printScheduler(scheduler));
        } catch (SchedulerException e) {
            throw new JobExecutionException(e);
        }

        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = scheduler.getContext();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        buf.append("\n  Scheduler.schedulerContext = " + new HashMap(schedulerContext));

        // 获取 Trigger
        Trigger trigger = context.getTrigger();
        buf.append("\n  Triger = " + Util.printTrigger(trigger));
        JobDataMap triggerJobDataMap = trigger.getJobDataMap();
        buf.append("\n  Triger.jobDataMap = " + new HashMap(triggerJobDataMap));

        // 获取JobDetail
        JobDetail jobDetail = context.getJobDetail();
        buf.append("\n  JobDetail = " + Util.printJobDetail(jobDetail));
        buf.append("\n  JobDetail.jobExecutionContext = " + context);

        // 从 JobDataMap 中读取配置信息
        JobDataMap jobDetailJobDataMap = jobDetail.getJobDataMap();
        buf.append("\n  JobDetail.jobDataMap = "
                + new HashMap(jobDetailJobDataMap));
        log.info(buf.toString());

        // TODO 结束日志
    }

}
