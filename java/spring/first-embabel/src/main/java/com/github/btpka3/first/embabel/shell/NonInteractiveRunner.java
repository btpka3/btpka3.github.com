package com.github.btpka3.first.embabel.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class NonInteractiveRunner implements ApplicationRunner {

    private final CliShell cliShell;

    @Override
    public void run(ApplicationArguments arguments) {
        List<String> args = arguments.getNonOptionArgs();
        if (args.isEmpty()) {
            return;
        }
        String command = args.get(0);
        List<String> shellArgs = args.subList(1, args.size());
        String result = switch (command) {
            case "ask" -> cliShell.ask(String.join(" ", shellArgs));
            case "tools" -> cliShell.tools();
            case "skills" -> cliShell.skills();
            case "mcps" -> cliShell.mcps();
            case "memory" -> cliShell.memory();
            case "good" -> cliShell.good();
            case "bad" -> cliShell.bad();
            case "help" -> cliShell.help();
            default -> null;
        };
        if (result == null) {
            return;
        }
        System.out.println(result);
        System.exit(0);
    }
}
