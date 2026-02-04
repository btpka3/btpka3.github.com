package me.test.jdk.java.util.logging;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/4/8
 * @see java.util.logging.LogManager#getConfigurationFileName()
 * @see java.util.logging.LogManager#readConfiguration(java.io.InputStream) ()
 */
@Slf4j
public class LoggerTest {

    @SneakyThrows
    @Test
    public void testJul() {
        LogManager.getLogManager().readConfiguration(LoggerTest.class.getResourceAsStream("/logging.properties"));
        //        SLF4JBridgeHandler.removeHandlersForRootLogger();
        //        SLF4JBridgeHandler.install();

        System.out.println("start.");
        {
            Logger logger = Logger.getLogger(LoggerTest.class.getName());
            logger.warning("testJul111");
            logger.log(Level.INFO, "testJul222");
        }
        {
            log.info("slf4j_logger_111");
            log.error("slf4j_logger_222");
        }
        System.out.println("done.");
        Thread.sleep(5 * 1000);
    }
}
