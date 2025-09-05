package me.test.my.spring.shell;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.shell.command.annotation.EnableCommand;

@SpringBootApplication
@EnableCommand(MyCommands2.class)
public class MyShellApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyShellApp.class, args);
    }

}
