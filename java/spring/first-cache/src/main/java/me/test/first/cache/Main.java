
package me.test.first.cache;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    private static boolean exit = false;

    public static void main(String[] args) {

        final ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("db-context.xml");
        appCtx.registerShutdownHook();

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                exit = true;
                System.out.println("Exiting...");
            }
        });

        // M2Eclipse could not pass Ctrl+C to this programm，
        // using console input instead.
        System.out.println("Running... Press enter to stop.");
        try {
            while (!exit && System.in.available() == 0) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (appCtx.isRunning()) {
            appCtx.close();
        }
        System.out.println("Stoped.");
    }
}
