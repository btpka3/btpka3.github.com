package me.test.my.spring.shell;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.util.Arrays;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/2
 */
@Command(group = "aaa")
public class MyCommands2 {
    @Command(command = "example", description = "description001")
    public String example() {
        return "Hello";
    }

    @Command(command = "add2", description = "myAdd demo")
    public int add2(
            @Option(longNames = "number1", description = "the first number") int n1,
            @Option(longNames = "number2", description = "the second number") int n2
    ) {
        return n1 + n2;
    }
    @Command(command = "add2", description = "myAdd demo")
    public int add3(
            @Option(longNames = "numbers", description = "the number list") int ... numbers
    ) {
        return Arrays.stream(numbers).sum();
    }
}
