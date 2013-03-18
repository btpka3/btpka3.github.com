package me.test.first.quartz;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EchoJob implements Job {

    private static Logger log = LoggerFactory.getLogger(EchoJob.class);

    public EchoJob() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        log.info("Hello World! - " + new Date());

        // FIXME How to invoke some Spring managed bean on same/another JVM ?
    }

}
