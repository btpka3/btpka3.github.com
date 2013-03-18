package me.test.first.quartz;

import me.test.first.quartz.a.QuartzDemo;

import org.quartz.SchedulerException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {

    public static void main(String[] args) throws Exception {

        String cmd = args[0];
        if ("startDb".equals(cmd)) {
            new ClassPathXmlApplicationContext("context-startDb.xml");
        } else if ("simpleJob".equals(cmd)) {
            String instanceId = args[1];
            String action = args[2];

            final QuartzDemo demo = new QuartzDemo(instanceId);

            if ("insertJob".equals(action)) {
                demo.deleteJob();
                demo.insertJob();
                Runtime.getRuntime().exit(0);
            } else if ("updateJob".equals(action)) {
                demo.updateJob();
                Runtime.getRuntime().exit(0);
            } else if ("deleteJob".equals(action)) {
                demo.deleteJob();
            } else if ("start".equals(action)) {
                demo.start();
            } else if ("shutdown".equals(action)) {
                demo.shutdown();
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

    }
}
