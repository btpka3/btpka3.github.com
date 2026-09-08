package com.github.btpka3.first.embabel.tool;

import com.github.btpka3.first.embabel.config.FirstEmbabelProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandExecutorTest {

    @Test
    void shouldAllowWhitelistedCommandAndCaptureOutput() {
        FirstEmbabelProperties properties = new FirstEmbabelProperties();
        properties.getCommands().getWhitelist().add("echo hello");

        CommandExecutor executor = new CommandExecutor(properties);
        String output = executor.execute("echo hello");
        assertThat(output.trim()).isEqualTo("hello");
    }

    @Test
    void shouldRejectCommandNotInWhitelist() {
        FirstEmbabelProperties properties = new FirstEmbabelProperties();
        properties.getCommands().getWhitelist().add("echo hello");

        CommandExecutor executor = new CommandExecutor(properties);
        assertThatThrownBy(() -> executor.execute("echo danger"))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining("Command not in whitelist");
    }
}
