package me.test.first.quartz;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class Util {
    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss.SSS");

    public static String printScheduler(Scheduler scheduler)
            throws SchedulerException {

        Map<String, Object> info = new LinkedHashMap<String, Object>();

        String schedulerName = scheduler.getSchedulerName();
        info.put("schedulerName", schedulerName);

        String schedulerInstanceId = scheduler.getSchedulerInstanceId();
        info.put("schedulerInstanceId", schedulerInstanceId);

        String calendarNames = scheduler.getCalendarNames().toString();
        info.put("calendarNames", calendarNames);

        return info.toString();
    }

    public static String printJobDetail(JobDetail jobDetail) {

        Map<String, Object> info = new LinkedHashMap<String, Object>();
        jobDetail.getDescription();
        JobKey jobKey = jobDetail.getKey();
        info.put("jobKey.group", jobKey.getGroup());
        info.put("jobKey.name", jobKey.getName());

        jobDetail.getJobClass();
        info.put("jobClass", jobDetail.getJobClass());
        info.put("concurrentExectionDisallowed",
                jobDetail.isConcurrentExectionDisallowed());
        info.put("persistJobDataAfterExecution",
                jobDetail.isPersistJobDataAfterExecution());
        info.put("isDurable", jobDetail.isDurable());
        info.put("requestsRecovery", jobDetail.requestsRecovery());

        return info.toString();
    }

    public static String printTrigger(Trigger trigger) {

        Map<String, Object> info = new LinkedHashMap<String, Object>();

        JobKey jobKey = trigger.getJobKey();
        info.put("jobKey.group", jobKey.getGroup());
        info.put("jobKey.name", jobKey.getName());

        TriggerKey triggerKey = trigger.getKey();
        info.put("triggerKey.group", triggerKey.getGroup());
        info.put("triggerKey.name", triggerKey.getName());
        info.put("priority", trigger.getPriority());
        info.put("calendarName", trigger.getCalendarName());
        info.put("misfireInstruction", trigger.getMisfireInstruction());
        info.put("description", trigger.getDescription());

        info.put("startTime", sdf.format(trigger.getStartTime()));
        info.put("endTime", sdf.format(trigger.getEndTime()));
        info.put("previousFireTime", sdf.format(trigger.getPreviousFireTime()));
        info.put("finalFireTime", sdf.format(trigger.getFinalFireTime()));
        info.put("nextFireTime", sdf.format(trigger.getNextFireTime()));

        return info.toString();
    }
}
