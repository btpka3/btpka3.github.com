package me.test.first.quartz;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuartzTestMain {
    Logger log = LoggerFactory.getLogger(QuartzTestMain.class);

    public void inserJobA() throws Exception {

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        String schedId = sched.getSchedulerInstanceId();

        // define the job and tie it to our HelloJob class
        JobDetail jobA = JobBuilder.newJob(HelloJob.class)
                .withIdentity("jobA", schedId).requestRecovery().build();
        
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1", schedId)
                .startAt(DateBuilder.futureDate(2, IntervalUnit.SECOND))
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withRepeatCount(20).withIntervalInSeconds(5))
                .build();
    }

    public void run() throws Exception {

        log.info("------- Initializing ----------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.warn("***** Deleting existing jobs/triggers *****");
        sched.clear();

        log.info("------- Initialization Complete -----------");

        // computer a time that is on the next round minute
        Date runTime = DateBuilder.evenMinuteDate(new Date());

        log.info("------- Scheduling Jobs -------------------");

        String schedId = sched.getSchedulerInstanceId();

        // define the job and tie it to our HelloJob class
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", schedId).requestRecovery().build();

        // Trigger the job to run on the next round minute
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1", schedId)
                .startAt(DateBuilder.futureDate(2, IntervalUnit.SECOND))
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withRepeatCount(20).withIntervalInSeconds(5))
                .build();

        // Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + runTime);

        // Start up the scheduler (nothing can actually run until the
        // scheduler has been started)
        sched.start();

        log.info("------- Started Scheduler -----------------");

        // wait long enough so that the scheduler as an opportunity to
        // run the job!
        log.info("------- Waiting 65 seconds... -------------");
        try {
            // wait 65 seconds to show job
            Thread.sleep(65L * 1000L);
            // executing...
        } catch (Exception e) {
        }

        // shut down the scheduler
        log.info("------- Shutting Down ---------------------");
        sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");
    }

    /*
     * 1. createDb
     * 2. insertJob
     * 3. startScheduleA
     * 4. startScheduleB
     * 5. stopScheduleA
     * 6. stopScheduleB
     */
    public static void main(String[] args) throws Exception {
        String s = "AAA";
        if (args.length > 0) {
            s = args[0];
        }
        while (true) {
            System.out.println(s);
            Thread.sleep(1000);
        }
    }

    /**
     * Step1. 启动数据库。 Step2. 向数据库中插入定期job1。 Step3. 以 JVM1 启动job1，确认所有任务都在此JVM上执行。
     * Step4. 以 JVM2 启动job1，确认任务分别有JVM1和JVM2执行。 Step5. 停止 JVM1。确认所有任务都在JVM2上运行。
     *
     * 任务类别： 1. 能否并发。 2. 能否重复执行（错误恢复）。 3. 任务有先后顺序，有依赖性，时间依赖性。 4.
     * 开机自启动、指定间隔启动、指定cron日期启动。
     *
     */
    public static void main1(String[] args) throws Exception {

        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();

        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].getFile());
        }
        ApplicationContext appCtxt = new ClassPathXmlApplicationContext(
                "classpath:/applicationContext.xml");

        QuartzTestMain example = new QuartzTestMain();
        example.run();

    }

}
