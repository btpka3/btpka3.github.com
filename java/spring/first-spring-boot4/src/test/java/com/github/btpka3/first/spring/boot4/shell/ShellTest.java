package com.github.btpka3.first.spring.boot4.shell;

import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.core.command.AbstractCommand;
import org.springframework.shell.core.command.CommandContext;
import org.springframework.shell.core.command.ExitStatus;
import org.springframework.shell.core.command.annotation.Argument;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.shell.core.command.availability.Availability;
import org.springframework.shell.core.command.availability.AvailabilityProvider;
import org.springframework.shell.core.command.exit.ExitStatusExceptionMapper;

/**
 *
 * @author dangqian.zll
 * @date 2026/3/20
 */
public class ShellTest {
    @Command(
            name = "example",
            description = "Say hi to a given name",
            group = "My Commands",
            help = "A command that greets the user with 'Hi ${name}!' with a configurable suffix. Example usage: hi -s=! John",
            availabilityProvider = "downloadAvailability"
    )
    public String example(
            CommandContext commandContext,
            @Argument(
                    index = 0,
                    description = "the name of the person to greet",
                    defaultValue = "world"
            )
            String name,

            @Size(min = 8, max = 40)
            @Option(
                    shortName = 's',
                    longName = "suffix",
                    description = "the suffix of the greeting message",
                    defaultValue = "!"
            )
            String suffix

    ) {
        return "Hello";
    }

    boolean connected = true;

    @Bean
    public AvailabilityProvider downloadAvailability() {
        return () -> connected ? Availability.available() : Availability.unavailable("you are not connected");
    }


    @Bean
    public AbstractCommand download() {
        return org.springframework.shell.core.command.Command.builder()
                .name("download")
                .availabilityProvider(
                        () -> connected ? Availability.available() : Availability.unavailable("you are not connected"))
                .execute(ctx -> {
                    // do something
                });
    }

    @Bean
    public ExitStatusExceptionMapper myCustomExceptionMapper() {
        return exception -> new ExitStatus(42, "42! The answer to life, the universe and everything!");
    }
}

