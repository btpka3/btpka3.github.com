package com.github.btpka3.first.spring.boot3.shell;


import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.EnableCommand;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/31
 */
@EnableCommand(DemoCommand.class)
@Command(command = "mycommand", alias = "myalias")
public class DemoCommand {
    @Command(command = "", alias = "")
    void myMainCommand() {
    }

    @Command(command = "mysubcommand", alias = {"myalias1", "myalias2"})
    public String mysubcommand() {
        return "Hello";
    }
}
