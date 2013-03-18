package me.test.first.quartz.a;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzDemo {

    private Logger log = LoggerFactory.getLogger(QuartzDemo.class);

    private Scheduler sched = null;

    /**
     * 示例主函数。接受一个参数，含义是与当前类同个package下的quartz资源文件的文件名。 <code>
     *  Usage: java QuartzDemo [|] [insertJob|start]
     * </code>
     *
     * @param args
     * @throws SchedulerException
     */
    public static void main(String[] args) throws SchedulerException {

        String filename = args[0];
        final QuartzDemo demo = new QuartzDemo(filename);

        String command = args[1];

        if ("insertJob".equals(command)) {
            demo.insertJob();
        } else if ("start".equals(command)) {
            demo.start();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    demo.shutdown();
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public QuartzDemo(String filename) throws SchedulerException {
        Properties props = new Properties();
        try {
            props.load(QuartzDemo.class.getResourceAsStream(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SchedulerFactory sf = new StdSchedulerFactory(props);
        try {
            sched = sf.getScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws SchedulerException {
        sched.start();
    }

    public void shutdown() throws SchedulerException {
        if (sched.isStarted() && !sched.isShutdown()) {
            sched.shutdown();
        }
    }

    /**
     * 新建一个Job，并为其分配一个trigger。
     *
     * @throws SchedulerException
     */
    public void insertJob() throws SchedulerException {

        String schedId = sched.getSchedulerInstanceId();

        // new job
        String jobName = "jobA";
        String jobGroup = schedId;
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobA.data.key[1]", "1");
        jobDataMap.put("jobA.data.key[2]", "1");

        JobDetail jobDetail = JobBuilder.newJob(JobA.class)
                .withIdentity(jobKey).usingJobData(jobDataMap)
                .requestRecovery().build();
        sched.addJob(jobDetail, true);

        // setup trigger
        updateJob();

    }

    /**
     *
     * @throws SchedulerException
     */
    public void updateJob() throws SchedulerException {

        String schedId = sched.getSchedulerInstanceId();

        String jobName = "jobA";
        String jobGroup = schedId;
        JobKey jobKey = new JobKey(jobName, jobGroup);

        // delete previous triggers
        List<? extends Trigger> triggers = sched.getTriggersOfJob(jobKey);
        int preInteval = 0;
        if (triggers != null) {
            for (Trigger trigger : triggers) {
                try {
                    preInteval = trigger.getJobDataMap().getInt("jobA.data[3]");
                } catch (Exception e) {
                    log.error("Could not found previous data for jobA", e);
                }
                TriggerKey triggerKey = trigger.getKey();
                sched.unscheduleJob(triggerKey);
            }
        }
        int inteval = preInteval;
        inteval++;
        inteval = preInteval % 5 + 5; // 5~9

        // new trigger for the job
        String triggerName = "jobA.trigger";
        String triggerGroup = schedId;
        Date startTime = DateBuilder.futureDate(2, IntervalUnit.SECOND);

        // CalendarIntervalScheduleBuilder
        // CronScheduleBuilder
        // DailyTimeIntervalScheduleBuilder
        // SimpleScheduleBuilder
        ScheduleBuilder<? extends Trigger> scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule().withRepeatCount(2000)
                .withIntervalInSeconds(inteval);

        JobDataMap triggerJobDataMap = new JobDataMap();
        triggerJobDataMap.put("jobA.data[2]", "22");
        triggerJobDataMap.put("jobA.data[3]", Integer.toString(inteval));
        Date endTime = DateBuilder.futureDate(30, IntervalUnit.MINUTE);
        Trigger trigger = TriggerBuilder.newTrigger().forJob(jobKey)
                .withIdentity(triggerName, triggerGroup).startAt(startTime)
                .withSchedule(scheduleBuilder).endAt(endTime)
                .usingJobData(triggerJobDataMap).build();

        sched.scheduleJob(trigger);
    }

    /**
     * 删除指定的Job以及其关联的Trigger。
     *
     * @throws SchedulerException
     */
    public void deleteJob() throws SchedulerException {

        String schedId = sched.getSchedulerInstanceId();
        String triggerName = "jobA.trigger";
        String triggerGroup = schedId;
        sched.unscheduleJob(new TriggerKey(triggerName, triggerGroup));

    }

}
