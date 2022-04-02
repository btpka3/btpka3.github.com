package com.github.btpka3.first.felix.my.app;


import org.apache.felix.framework.FrameworkFactory;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 当千
 * @date 2019-04-04
 */
public class App {

    static Framework framework = null;

    public static void main(String[] args) throws BundleException, InterruptedException {

        Runtime.getRuntime().addShutdownHook(new Thread("Felix Shutdown Hook") {
            @Override
            public void run() {
                try {
                    if (framework != null) {
                        framework.stop();
                        framework.waitForStop(0);
                    }
                } catch (Exception ex) {
                    System.err.println("Error stopping framework: " + ex);
                }
            }
        });

        FrameworkFactory factory = null;
        Map configuration = new HashMap(8);
//        configuration.put(AutoProcessor.AUTO_DEPLOY_DIR_PROPERY,)
        Framework framework = factory.newFramework(configuration);
        framework.init();

        //AutoProcessor.process(null, m_fwk.getBundleContext());

        framework.start();
        framework.waitForStop(0);
        System.exit(0);
    }
}
