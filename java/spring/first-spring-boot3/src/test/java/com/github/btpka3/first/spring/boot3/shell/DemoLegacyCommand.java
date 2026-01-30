package com.github.btpka3.first.spring.boot3.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/31
 */
@ShellComponent
@ShellCommandGroup("myGroup1")
public class DemoLegacyCommand {
    @ShellMethod(key = "greet", value = "Greet the user")
    public String greet(@ShellOption(help = "Your name", defaultValue = "World") String name) {
        return null;
    }
}
