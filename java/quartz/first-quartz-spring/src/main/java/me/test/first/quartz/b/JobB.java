package me.test.first.quartz.b;

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
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobB extends QuartzJobBean {

    private Logger log = LoggerFactory.getLogger(JobB.class);

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {

        log.info("JobExecutionContext = " + context);

        // 获取 Trigger
        Trigger trigger = context.getTrigger();
        log.info("Triger = " + trigger);

        // 获取JobDetail
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        // 从 JobDataMap 中读取配置信息
        String value = jobDataMap.getString("sampleKey");
        log.info("JobDataMap['sampleKey'] = " + value);

        Scheduler scheduler = context.getScheduler();
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = scheduler.getContext();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        // 获取 Spring 管理的其他Bean
        Runnable runnable = (Runnable) schedulerContext.get("runnable");
        runnable.run();

        // 获取 ApplicationContext
        ApplicationContext appContext = (ApplicationContext) schedulerContext
                .get("applicationContext");
        log.info("ApplicationContext = " + appContext);

    }

    public void insertJob() {

    }

    public void updateJob() {

    }

    public void deleteJob() {

    }

}
