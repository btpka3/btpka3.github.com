package me.test.my.spring.shell;

import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;
import org.springframework.shell.*;
import org.springframework.shell.standard.*;

import java.util.*;

@ShellComponent
@Slf4j
public class MyCommands {

    // 默认使用方法名作为命令行的 命令名称，可以通过 key 来明确指定。
    // 命令太多时，可以通过 group 来分组
    @ShellMethod("Add two integers together.")
    public int add(int a, int b) {
        log.info("add 1 : a = {}, b = {}", a, b);

        return a + b;
    }

    @ShellMethod("Add Numbers.")
    public float add2(@ShellOption(arity = 3) float[] numbers) {
        log.info("add2: numbers = {}", Arrays.asList(ArrayUtils.toObject(numbers)));
        return numbers[0] + numbers[1] + numbers[2];
    }

    // 默认使用参数名作为 命令行参数名。可以通过 @ShellOption 来明确指定
    @ShellMethod("Display stuff.")
    public String echo(
            int a,
            int b,

            @ShellOption({"-C", "--command"})
                    int c
    ) {
        return String.format("You said a=%d, b=%d, c=%d", a, b, c);
    }


    @ShellMethod("Terminate the system.")
    public String shutdown(boolean force) {
        return "You said " + force;
    }

    private boolean connected;

    @ShellMethod("Connect to the server.")
    public void connect(String user, String password) {

        connected = true;
    }

    @ShellMethod("Download the nuclear codes.")
    // 默认使用过 方法名 +"Availability" 方法来检查该命令的可用性的。
    // 但也可以明确指定
    // @ShellMethodAvailability("downloadAvailability")
    public void download() {
        log.info("start download");
    }

    public Availability downloadAvailability() {
        return connected
                ? Availability.available()
                : Availability.unavailable("you are not connected");
    }

}