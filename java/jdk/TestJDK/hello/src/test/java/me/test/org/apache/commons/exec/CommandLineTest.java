package me.test.org.apache.commons.exec;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.time.Duration;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/4
 */
public class CommandLineTest {
    @Test
    public void testEcho01() {
        String result = exec("echo 123");
        System.out.println("result = " + result);
        Assertions.assertEquals("123\n", result);
    }

    @Test
    public void testEcho02() {
        Assertions.assertEquals("\"123 456\"\n", exec("echo \"123 456\""));
    }

    @Test
    public void testEcho03() {
        CommandLine cmdLine = new CommandLine("echo");
        cmdLine.addArgument("123 456");
        Assertions.assertEquals("\"123 456\"\n", exec(cmdLine));
    }

    @Test
    public void commandLineParse01() {
        String cmd = "echo \"123 456\"";
        // ⚠️ 默认使用 handleQuoting=true，故如果要处理好双引号，
        // 则建议 手动调用 CommandLine#addArgument(String,boolean) 对象
        // 对于要调用的命令行参数的配置文档的也做好设计： ✅String[]， ❌单个String
        CommandLine cmdLine = CommandLine.parse(cmd);
        Assertions.assertEquals("echo", cmdLine.getExecutable());
        String[] args = cmdLine.getArguments();
        Assertions.assertEquals(1, args.length);
        // FIXME 有双引号
        Assertions.assertEquals("\"123 456\"", args[0]);
    }

    @Test
    public void commandLineParse01_2() {
        String cmd = "echo \"123 456\"";
        CommandLine cmdLine = CommandLine.parse(cmd);
        Assertions.assertEquals("echo", cmdLine.getExecutable());
        String[] args = cmdLine.getArguments();
        Assertions.assertEquals(1, args.length);
        // FIXME 有双引号
        Assertions.assertEquals("\"123 456\"", args[0]);
    }

    @Test
    public void commandLineParse02() {
        CommandLine cmdLine = new CommandLine("echo");
        // ⭕️: handleQuoting=false
        cmdLine.addArgument("123 456", false);
        String[] strs = cmdLine.toStrings();

        Assertions.assertEquals("echo", strs[0]);
        Assertions.assertEquals("123 456", strs[1]);
        Assertions.assertEquals("123 456\n", exec(cmdLine));
    }


    protected String exec(String cmd) {
        return exec(cmd, 0);
    }

    protected String exec(CommandLine cmdLine) {
        return exec(cmdLine, 0);
    }

    protected String exec(String cmd, long timeoutMillis) {
        CommandLine cmdLine = CommandLine.parse(cmd);
        return exec(cmdLine, timeoutMillis);
    }

    protected String exec(CommandLine cmdLine, long timeoutMillis) {
        DefaultExecutor executor = DefaultExecutor.builder().get();

        if (timeoutMillis > 0) {
            ExecuteWatchdog watchdog = ExecuteWatchdog.builder()
                    .setTimeout(Duration.ofMillis(timeoutMillis))
                    .get();
            executor.setWatchdog(watchdog);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);

        try {
            executor.execute(cmdLine);
            return outputStream.toString();
        } catch (Exception e) {
            throw new RuntimeException("failed to exec cmd =`" + cmdLine.toString() + "`", e);
        }
    }

}
